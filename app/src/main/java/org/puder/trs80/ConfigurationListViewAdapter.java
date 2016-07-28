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

package org.puder.trs80;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.tekle.oss.android.animation.AnimationFactory;

import org.puder.trs80.drag.ItemTouchHelperAdapter;

public class ConfigurationListViewAdapter extends
        RecyclerView.Adapter<ConfigurationListViewAdapter.Holder> implements ItemTouchHelperAdapter {

    private ConfigurationItemListener listener;
    private boolean                   usesGridLayout;


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        int positionOffset = usesGridLayout ? 0 : -1;
        Configuration.move(fromPosition + positionOffset, toPosition + positionOffset);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

    }


    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public boolean        draggable;
        public int            position;
        public Configuration  configuration;
        public TextView       nameFront;
        public TextView       nameBack;
        public TextView       model;
        public TextView       disks;
        public TextView       cassette;
        public TextView       sound;
        public TextView       keyboards;
        public ScreenshotView screenshot;
        public ViewFlipper    viewFlipper;
        public View           stopButton;


        public Holder(View itemView) {
            super(itemView);
            if (!(itemView instanceof RelativeLayout)) {
                draggable = false;
                return;
            }
            draggable = true;
            nameFront = (TextView) itemView.findViewById(R.id.configuration_name_front);
            nameBack = (TextView) itemView.findViewById(R.id.configuration_name_back);
            model = (TextView) itemView.findViewById(R.id.configuration_model);
            disks = (TextView) itemView.findViewById(R.id.configuration_disks);
            cassette = (TextView) itemView.findViewById(R.id.configuration_cassette);
            sound = (TextView) itemView.findViewById(R.id.configuration_sound);
            keyboards = (TextView) itemView.findViewById(R.id.configuration_keyboards);
            screenshot = (ScreenshotView) itemView.findViewById(R.id.configuration_screenshot);
            viewFlipper = (ViewFlipper) itemView.findViewById(R.id.configuration_view_flipper);
            viewFlipper.setDisplayedChild(0);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.configuration_info).setOnClickListener(this);
            itemView.findViewById(R.id.configuration_back).setOnClickListener(this);
            itemView.findViewById(R.id.configuration_edit).setOnClickListener(this);
            itemView.findViewById(R.id.configuration_delete).setOnClickListener(this);
            itemView.findViewById(R.id.configuration_run).setOnClickListener(this);
            stopButton = itemView.findViewById(R.id.configuration_stop);
            stopButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.configuration_info:
            case R.id.configuration_back:
                AnimationFactory.flipTransition(viewFlipper,
                        AnimationFactory.FlipDirection.LEFT_RIGHT);
                break;
            case R.id.configuration_edit:
                listener.onConfigurationEdit(configuration, position);
                break;
            case R.id.configuration_stop:
                listener.onConfigurationStop(configuration, position);
                break;
            case R.id.configuration_delete:
                listener.onConfigurationDelete(configuration, position);
                break;
            case R.id.configuration_run:
                listener.onConfigurationRun(configuration, position);
                break;
            default:
                if (viewFlipper.getDisplayedChild() == 0) {
                    listener.onConfigurationRun(configuration, position);
                }
                break;
            }
        }
    }


    public ConfigurationListViewAdapter(boolean usesGridLayout, ConfigurationItemListener listener) {
        this.usesGridLayout = usesGridLayout;
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(viewType, viewGroup, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        if (!usesGridLayout && (position == 0 || position == Configuration.getCount() + 1)) {
            return;
        }

        int positionOffset = usesGridLayout ? 0 : -1;
        Configuration conf = Configuration.getNthConfiguration(position + positionOffset);

        if (holder.position != position && holder.viewFlipper.getDisplayedChild() != 0) {
            Context context = TRS80Application.getAppContext();
            holder.viewFlipper.setInAnimation(context, R.anim.no_animation);
            holder.viewFlipper.setOutAnimation(context, R.anim.no_animation);
            holder.viewFlipper.setDisplayedChild(0);
        }

        holder.stopButton.setVisibility(EmulatorState.hasSavedState(conf.getId()) ? View.VISIBLE
                : View.GONE);

        // Position
        holder.position = position;

        // Configuration
        holder.configuration = conf;

        // Name
        String name = conf.getName();
        holder.nameFront.setText(name);
        holder.nameBack.setText(name);

        // Hardware
        String model = "-";
        switch (conf.getModel()) {
        case Hardware.MODEL1:
            model = "Model I";
            break;
        case Hardware.MODEL3:
            model = "Model III";
            break;
        case Hardware.MODEL4:
            model = "Model 4";
            break;
        case Hardware.MODEL4P:
            model = "Model 4P";
            break;
        }
        holder.model.setText(model);

        // Disks
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (conf.getDiskPath(i) != null) {
                count++;
            }
        }
        holder.disks.setText(Integer.toString(count));

        // Cassette
        holder.cassette.setText(conf.getCassettePosition() > 0 ? R.string.cassette_not_rewound
                : R.string.cassette_rewound);

        // Sound
        holder.sound.setText(conf.muteSound() ? R.string.sound_disabled : R.string.sound_enabled);

        // Keyboards
        String keyboards = getKeyboardLabel(conf.getKeyboardLayoutPortrait());
        keyboards += "/";
        keyboards += getKeyboardLabel(conf.getKeyboardLayoutLandscape());
        holder.keyboards.setText(keyboards);

        // Screenshot
        holder.screenshot.setScreenshotBitmap(null);
        new AsyncTask<Integer, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Integer... params) {
                return EmulatorState.loadScreenshot(params[0]);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                holder.screenshot.setScreenshotBitmap(result);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, conf.getId());
    }

    @Override
    public int getItemViewType(int position) {
        if (!usesGridLayout && position == 0) {
            return R.layout.configuration_header;
        }
        if (!usesGridLayout && position == Configuration.getCount() + 1) {
            return R.layout.configuration_footer;
        }
        return R.layout.configuration_item;
    }

    @Override
    public int getItemCount() {
        int count = Configuration.getCount();
        if (!usesGridLayout) {
            count += 2; /* header + footer */
        }
        return count;
    }

    private String getKeyboardLabel(int type) {
        int id;
        switch (type) {
        case Configuration.KEYBOARD_LAYOUT_ORIGINAL:
            id = R.string.keyboard_abbrev_original;
            break;
        case Configuration.KEYBOARD_LAYOUT_COMPACT:
            id = R.string.keyboard_abbrev_compact;
            break;
        case Configuration.KEYBOARD_LAYOUT_JOYSTICK:
            id = R.string.keyboard_abbrev_joystick;
            break;
        case Configuration.KEYBOARD_TILT:
            id = R.string.keyboard_abbrev_tilt;
            break;
        case Configuration.KEYBOARD_GAME_CONTROLLER:
            id = R.string.keyboard_abbrev_game_controller;
            break;
        default:
            return "-";
        }
        return TRS80Application.getAppContext().getString(id);
    }
}