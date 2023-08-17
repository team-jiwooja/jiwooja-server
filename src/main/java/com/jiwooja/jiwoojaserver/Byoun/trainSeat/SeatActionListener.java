/*
package com.jiwooja.jiwoojaserver.Byoun.trainSeat;

import org.springframework.stereotype.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JToggleButton;

@Component
public class SeatActionListener implements ActionListener {

    private final int people;
    private final List<String> list;

    public SeatActionListener1(@Value("${seat.people}") int people, List<String> list) {
        this.people = people;
        this.list = list;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = ((JToggleButton) e.getSource()).getText();
        if (((JToggleButton) e.getSource()).isSelected()) {
            if (people > list.size()) {
                list.add(buttonText);
                SpecialSeatSelect.setLabel(list.toString());
            }
        } else {
            list.remove(list.indexOf(buttonText));
            SpecialSeatSelect.setLabel(list.toString());
        }
    }
}
*/
