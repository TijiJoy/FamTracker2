package projects.tiji.com.famtracker;

/**
 * Created by Tg on 15-05-2018.
 */

public class CircleJoin {
    public String name;
    public String circlememberid;

    public CircleJoin(String name, String circlememberid) {
        this.name = name;
        this.circlememberid = circlememberid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public CircleJoin() {
    }

    public CircleJoin(String circlememberid) {
        this.circlememberid = circlememberid;

    }

    public String getCirclememberid() {

        return circlememberid;
    }

    public void setCirclememberid(String circlememberid) {
        this.circlememberid = circlememberid;
    }


}
