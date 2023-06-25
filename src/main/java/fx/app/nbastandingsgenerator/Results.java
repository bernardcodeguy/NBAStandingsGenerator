package fx.app.nbastandingsgenerator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Results {
    private String get;
    private Map<String, String> parameters;
    private List<String> errors;
    private int results;
    private List<Response> response;

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public static class Response {
        private String league;
        private int season;
        private Team team;
        private Conference conference;
        private Division division;
        private WinLoss win;
        private WinLoss loss;
        private String gamesBehind;
        private int streak;
        private boolean winStreak;
        private Object tieBreakerPoints;

        private double PCT;

        public double getPCT() {
            int winsTotal = this.win.getTotal();
            int totalGamesPlayed =  winsTotal + this.getLoss().getTotal();
            Double pct = (double) winsTotal / totalGamesPlayed;

            return pct;
        }



        public String getLeague() {
            return league;
        }

        public void setLeague(String league) {
            this.league = league;
        }

        public int getSeason() {
            return season;
        }

        public void setSeason(int season) {
            this.season = season;
        }

        public Team getTeam() {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }

        public Conference getConference() {
            return conference;
        }

        public void setConference(Conference conference) {
            this.conference = conference;
        }

        public Division getDivision() {
            return division;
        }

        public void setDivision(Division division) {
            this.division = division;
        }

        public WinLoss getWin() {
            return win;
        }

        public void setWin(WinLoss win) {
            this.win = win;
        }

        public WinLoss getLoss() {
            return loss;
        }

        public void setLoss(WinLoss loss) {
            this.loss = loss;
        }

        public String getGamesBehind() {
            return gamesBehind;
        }

        public void setGamesBehind(String gamesBehind) {
            this.gamesBehind = gamesBehind;
        }

        public int getStreak() {
            return streak;
        }

        public void setStreak(int streak) {
            this.streak = streak;
        }

        public boolean isWinStreak() {
            return winStreak;
        }

        public void setWinStreak(boolean winStreak) {
            this.winStreak = winStreak;
        }

        public Object getTieBreakerPoints() {
            return tieBreakerPoints;
        }

        public void setTieBreakerPoints(Object tieBreakerPoints) {
            this.tieBreakerPoints = tieBreakerPoints;
        }

        public static class Team {
            private int id;
            private String name;
            private String nickname;
            private String code;
            private String logo;

            private int rank;  // add this field

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }
        }

        public static class Conference {
            private String name;
            private int rank;
            private int win;
            private int loss;




            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public int getWin() {
                return win;
            }

            public void setWin(int win) {
                this.win = win;
            }

            public int getLoss() {
                return loss;
            }

            public void setLoss(int loss) {
                this.loss = loss;
            }
        }

        public static class Division {
            private String name;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class WinLoss {
            private int total;
            private int home;
            private int away;

            private int lastTen;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getHome() {
                return home;
            }

            public void setHome(int home) {
                this.home = home;
            }

            public int getAway() {
                return away;
            }

            public void setAway(int away) {
                this.away = away;
            }

            public int getLastTen() {
                return lastTen;
            }

            public void setLastTen(int lastTen) {
                this.lastTen = lastTen;
            }
        }
    }

    public void sortResponseByWinTotal() {
        // sort response list by highest win total value
        Collections.sort(response, new Comparator<Response>() {
            @Override
            public int compare(Response r1, Response r2) {
                return r2.getWin().getTotal() - r1.getWin().getTotal();
            }
        });

        // set rank based on order after sorting
        int rank = 1;
        for (Response r : response) {
            r.getTeam().setRank(rank++);
        }
    }
}