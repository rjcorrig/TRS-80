/*
 * Copyright 2012-2013, Arno Puder
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.puder.trs80.browser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.puder.trs80.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class FileBrowserActivity extends ListActivity {

    private List<String> items;
    private String       pathPrefix;
    private TextView     pathLabel;
    private String       currentPath;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.file_browser);
        int id = this.getResources().getIdentifier("path", "string", "org.puder.trs80");
        pathPrefix = this.getString(id) + ": ";
        pathLabel = (TextView) this.findViewById(R.id.path);
        getFiles(Environment.getExternalStorageDirectory().getPath());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String file = items.get((int) id);
        if (file.equals("..")) {
            int idx = currentPath.lastIndexOf("/");
            if (idx == 0) {
                idx++;
            }
            getFiles(currentPath.substring(0, idx));
            return;
        }
        if (new File(file).isDirectory()) {
            getFiles(file);
            return;
        }
        Intent i = getIntent();
        i.putExtra("PATH", file);
        setResult(RESULT_OK, i);
        finish();
    }

    private void getFiles(String path) {
        currentPath = path;
        pathLabel.setText(pathPrefix + currentPath);
        File[] files = new File(path).listFiles();
        items = new ArrayList<String>();
        if (!path.equals("/")) {
            items.add("..");
        }
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    items.add(file.getPath());
                }
            }
            for (File file : files) {
                if (!file.isDirectory()) {
                    items.add(file.getPath());
                }
            }
        }
        BrowserListViewAdapter fileList = new BrowserListViewAdapter(this, items);
        setListAdapter(fileList);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED, getIntent());
        finish();
    }
}