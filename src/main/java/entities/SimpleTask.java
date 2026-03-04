package entities;

public class SimpleTask extends Task{

    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, int startHour, int endHour)
    {
        super(idTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }
    public SimpleTask(int idTask, String statusTask, int startHour, int endHour)
    {
        super(idTask,statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour()
    {
        return startHour;
    }

    public int getEndHour()
    {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    @Override
    public int estimateDuration() {
        return endHour - startHour;
    }
}
