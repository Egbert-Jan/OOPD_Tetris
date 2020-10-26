package tetris;

import nl.han.ica.oopg.alarm.Alarm;
import nl.han.ica.oopg.alarm.IAlarmListener;

/**
 * The timer that makes the Tetrominos fall
 */
public class DecendTimer implements IAlarmListener {
    private Tetris world;

    public DecendTimer(Tetris world) {
        this.world = world;
        startAlarm();
    }

    private void startAlarm() {
        Alarm alarm = new Alarm("Fall Timer", 1);
        alarm.addTarget(this);
        alarm.start();
    }

    @Override
    public void triggerAlarm(String alarmName) {
        if(!world.gameIsStopped)
            world.handleGoDown();
            world.drawMap();
            startAlarm();
    }
}
