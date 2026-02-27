package com.example.pda.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ColorConsole {
    private static final String TAG = "MEIKO_PDA";
    private static String logFolderPath;
    private static boolean showLineNumberInLog = true;

    private static final String TYPE_SUCCESS = "SUCCESS";
    private static final String TYPE_ERROR   = "ERROR  ";
    private static final String TYPE_WARN    = "WARN   ";
    private static final String TYPE_INFO    = "INFO   ";

    private static final String TARGET_EMAIL = "superouvorlurd@gmail.com";

    public static void init(Context context) {
        File logsDir = new File(context.getExternalFilesDir(null), "Logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }
        logFolderPath = logsDir.getAbsolutePath();
        
        // 1. Dọn dẹp log cũ (> 5 ngày)
        cleanupOldLogs(5);
        
        // 2. Tự động gửi log của ngày hôm qua
        autoSendYesterdayLog();
    }

    private static void autoSendYesterdayLog() {
        if (logFolderPath == null) return;

        // Lấy ngày hôm qua
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterdayStr = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(cal.getTime());
        
        File yesterdayLog = new File(logFolderPath, yesterdayStr + ".log");

        if (yesterdayLog.exists()) {
            String subject = "PDA Log Report - " + yesterdayStr;
            String body = "Gửi từ máy quét PDA Meiko.\nBáo cáo log tự động ngày " + yesterdayStr;
            
            // Gửi mail trong background
            MailSender.sendFile(TARGET_EMAIL, subject, body, yesterdayLog.getAbsolutePath());
        }
    }

    private static void cleanupOldLogs(int daysLimit) {
        if (logFolderPath == null) return;
        File folder = new File(logFolderPath);
        File[] files = folder.listFiles();
        if (files == null) return;

        long limitTime = System.currentTimeMillis() - (daysLimit * 24L * 60L * 60L * 1000L);
        for (File file : files) {
            if (file.lastModified() < limitTime) {
                file.delete();
            }
        }
    }

    public static void Success(String text) {
        print(TYPE_SUCCESS, text, Log.INFO);
    }

    public static void Error(String text) {
        print(TYPE_ERROR, text, Log.ERROR);
    }

    public static void Error(String text, Throwable e) {
        print(TYPE_ERROR, text + "\n" + Log.getStackTraceString(e), Log.ERROR);
    }

    public static void Warn(String text) {
        print(TYPE_WARN, text, Log.WARN);
    }

    public static void Info(String text) {
        print(TYPE_INFO, text, Log.DEBUG);
    }

    public static void Start() {
        print("START  ", "======== START ========", Log.DEBUG);
    }

    public static void shareLogFile(Context context) {
        if (logFolderPath == null) return;
        String dateStr = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(new Date());
        File logFile = new File(logFolderPath, dateStr + ".log");

        if (!logFile.exists()) {
            Toast.makeText(context, "Không có dữ liệu log cho ngày hôm nay!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", logFile);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "PDA Log: " + dateStr);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Gửi báo cáo Log qua:"));
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi chia sẻ log", e);
        }
    }

    private static void print(String type, String text, int priority) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = null;
        for (int i = 1; i < stackTrace.length; i++) {
            String className = stackTrace[i].getClassName();
            if (!className.equals(ColorConsole.class.getName()) && !className.contains("java.lang.Thread")) {
                element = stackTrace[i];
                break;
            }
        }
        String fileName = (element != null) ? element.getFileName() : "Unknown";
        int lineNumber = (element != null) ? element.getLineNumber() : 0;
        String methodName = (element != null) ? element.getMethodName() : "";
        String logTag = String.format("%s:[%s:%d]", TAG, fileName, lineNumber);
        String logcatMsg = String.format("%s -> %s %s", methodName, getIcon(type), text);
        Log.println(priority, logTag, logcatMsg);
        String fileMsg = showLineNumberInLog ? String.format(" - %s <%s:%d>", text, fileName, lineNumber) : String.format(" - %s", text);
        writeLogToFile(type, fileMsg);
    }

    private static String getIcon(String type) {
        switch (type.trim()) {
            case TYPE_SUCCESS: return "✅";
            case TYPE_ERROR:   return "❌";
            case TYPE_WARN:    return "⚠️";
            case TYPE_INFO:    return "ℹ️";
            default:           return "🔹";
        }
    }

    private static void writeLogToFile(String type, String message) {
        if (logFolderPath == null) return;
        new Thread(() -> {
            String dateStr = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(new Date());
            String timeStr = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            File logFile = new File(logFolderPath, dateStr + ".log");
            String fullLine = String.format("%s %s %s", timeStr, type, message);
            synchronized (ColorConsole.class) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                    writer.write(fullLine);
                    writer.newLine();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to write log to file", e);
                }
            }
        }).start();
    }

    public static void Table(String title, List<String> headers, List<List<String>> rows) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n--- ").append(title).append(" ---\n");
        for (String h : headers) builder.append("| ").append(h).append(" ");
        builder.append("|\n");
        for (int i = 0; i < headers.size(); i++) builder.append("|---");
        builder.append("|\n");
        for (List<String> row : rows) {
            for (String cell : row) builder.append("| ").append(cell).append(" ");
            builder.append("|\n");
        }
        Info(builder.toString());
    }
}
