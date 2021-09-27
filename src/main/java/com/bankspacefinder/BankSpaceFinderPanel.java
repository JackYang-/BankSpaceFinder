package com.bankspacefinder;

import com.bankspacefinder.rules.Freeable;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.AsyncBufferedImage;

import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Slf4j
public class BankSpaceFinderPanel extends PluginPanel {

    private ItemManager itemManager;

    public BankSpaceFinderPanel (ItemManager itemManager){
        super();

        this.itemManager = itemManager;

        System.out.println("@@panel created");

        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());

        add(new JLabel("Visit a bank"));
    }

    public void setFreeables(List<Freeable> freeables) {
        this.removeAll();
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();

        for (int i = 0; i < freeables.size(); i ++) {
            //c1.fill = GridBagConstraints.HORIZONTAL;
            c1.insets = new Insets(0, 0, 15, 0);
            c1.anchor = GridBagConstraints.NORTHWEST;
            c1.gridx = 0;
            c1.gridy = i;
            final AsyncBufferedImage img = itemManager.getImage(freeables.get(i).getItemID());
            JLabel imgLabel = new JLabel();
            img.addTo(imgLabel);
            this.add(imgLabel, c1);
            //c2.fill = GridBagConstraints.BOTH;
            c2.insets = new Insets(0, 0, 15, 0);
            c2.gridx = 1;
            c2.gridy = i;
            JTextArea message = new JTextArea(freeables.get(i).getMessage(), 3, 20);
            message.setLineWrap(true);
            message.setWrapStyleWord(true);
            message.setOpaque(false);
            message.setEditable(false);
            this.add(message, c2);
        }
        this.updateUI();
    }
}
