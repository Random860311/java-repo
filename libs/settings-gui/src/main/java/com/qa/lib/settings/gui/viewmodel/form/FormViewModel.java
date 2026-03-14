package com.qa.lib.settings.gui.viewmodel.form;

import com.qa.lib.core.gui.viewmodel.base.ComponentViewModel;
import com.qa.lib.settings.dto.ConfigFileDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FormViewModel extends ComponentViewModel {
    private final ObservableList<FormRowViewModel> rows = FXCollections.observableArrayList();

    private final Map<String, Map<String, Map<String, Object>>> history = new HashMap<>();
    private String currentFileName;
    private String currentSection;

    public ObservableList<FormRowViewModel> getRows() {
        return rows;
    }


    public void setRows(String fileName, String section, @NonNull Map<String, Object> defaultValues) {
        clearRows();
        currentFileName = fileName;
        currentSection = section;

        Map<String, Object> values = defaultValues;

        if(history.containsKey(fileName) && history.get(fileName).containsKey(section)) {
            values = history.get(fileName).get(section);
        }
        else {
            if(history.containsKey(fileName)) {
                history.get(fileName).put(section, values);
            }
            else {
                Map<String, Map<String, Object>> newSection = new HashMap<>();
                newSection.put(section, values);
                history.put(fileName, newSection);
            }

        }
        addRows(values);
    }

    public void clearRows() {
        if(!history.containsKey(currentFileName)) {
            history.put(currentFileName, new HashMap<>());
        }
        if(!history.get(currentFileName).containsKey(currentSection)) {
            history.get(currentFileName).put(currentSection, new HashMap<>());
        }
        history.get(currentFileName).get(currentSection).putAll(getValues());

        rows.clear();
    }

    public @NonNull List<ConfigFileDto> getOpenedConfigFiles() {
        List<ConfigFileDto> configFiles = new ArrayList<>();
        for(Map.Entry<String, Map<String, Map<String, Object>>> fileEntry : history.entrySet()) {
            ConfigFileDto fileDto = new ConfigFileDto(fileEntry.getKey());

            for(Map.Entry<String, Map<String, Object>> sectionEntry : fileEntry.getValue().entrySet()) {
                fileDto.getConfigs().put(sectionEntry.getKey(), sectionEntry.getValue());
            }

            configFiles.add(fileDto);
        }
        return configFiles;
    }

    private Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        for (FormRowViewModel row : rows) {
            values.put(row.nameProperty().get(), row.valueProperty().get());
        }
        return values;
    }

    private void addRow(String name, String value) {
        rows.add(new FormRowViewModel(name, value));
    }

    private void addRows(@NonNull Map<String, Object> values) {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            addRow(entry.getKey(), entry.getValue().toString());
        }
    }
}
