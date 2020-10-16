import nl.han.ica.oopg.alarm.Alarm;
import nl.han.ica.oopg.alarm.IAlarmListener;

public class DecendTimer implements IAlarmListener {
    private Tetris world;

    public DecendTimer(Tetris world) {
        this.world = world;
        startAlarm();
    }

    private void startAlarm() {
        Alarm alarm = new Alarm("Fall Timer", 2);
        alarm.addTarget(this);
        alarm.start();
    }

    @Override
    public void triggerAlarm(String alarmName) {
        System.out.println("Timer");
        if(world.tryClearCurrentShape()) {
            world.currentTetromino.goDown();
        }
        world.drawMap();
        startAlarm();
    }
}
