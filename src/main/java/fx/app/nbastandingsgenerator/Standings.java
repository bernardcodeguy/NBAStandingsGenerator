package fx.app.nbastandingsgenerator;

public class Standings {
    private int rank,w,l;
    private String teamName,conf,home,away,l10,strk,pct;
    private double gb;


    public Standings(){}

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getL10() {
        return l10;
    }

    public void setL10(String l10) {
        this.l10 = l10;
    }

    public String getStrk() {
        return strk;
    }

    public void setStrk(String strk) {
        this.strk = strk;
    }

    public String getPct() {
        return pct;
    }

    public void setPct(String pct) {
        this.pct = pct;
    }

    public double getGb() {
        return gb;
    }

    public void setGb(double gb) {
        this.gb = gb;
    }
}
