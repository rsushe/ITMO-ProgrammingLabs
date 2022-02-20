package com.lab.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.lab.client.Data.Route;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.ArrayList;

public final class FileManager {
    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Route> readFromFile() {
        File file = new File(fileName);
        if (file.exists() && !file.canRead()) {
            System.out.println("Нет прав для чтения файла");
            return new ArrayList<>();
        } else {
            return JsonParser.parseJson(file);
        }
    }

    public void saveToFile(TreeSet<Route> treeSet) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))).setPrettyPrinting().create();
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName))) {
            String toPrint = gson.toJson(treeSet);
            out.write(toPrint.getBytes());
            System.out.println("Коллекция успешно сохранена в файл");
        } catch (IOException e) {
            System.out.println("Нет прав записи в файл. Коллекция не сохранена.");
        }
    }

    public static ArrayList<String> readScript(String scriptName) {
        File file = new File(scriptName);
        ArrayList<String> arrayList = new ArrayList<>();
        if (file.exists() && !file.canRead()) {
            System.out.println("Нет прав для чтения скрипта");
            return arrayList;
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                arrayList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Скрипта с таким именем не существует, проверьте правильность названия.");
        }
        return arrayList;
    }
}