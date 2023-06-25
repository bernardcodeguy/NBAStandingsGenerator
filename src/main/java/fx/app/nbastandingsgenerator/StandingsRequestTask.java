package fx.app.nbastandingsgenerator;

import javafx.concurrent.Task;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class StandingsRequestTask extends Task<List<Standings>> {
    private final int season;
    private final String conf;



    public StandingsRequestTask(int season, String conf) {
        this.season = season;
        this.conf = conf;
    }


    @Override
    protected List<Standings> call() throws Exception {
        String API_URL = "https://api-nba-v1.p.rapidapi.com/standings";
        String API_HOST = "api-nba-v1.p.rapidapi.com";
        String API_KEY = "b4bb6c8917mshfe1e342aa3a06d4p118726jsn749411abb51e";
        String conference;
        if(this.conf.equals("Western Conference")){
            conference = "west"; // add conference parameter for East
        }else{
            conference = "east";
        }

        String league = "standard";
        String season = String.valueOf(this.season);
        String query = String.format("?conference=%s&league=%s&season=%s", conference, league, season);
        URI uri = URI.create(API_URL + query);
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("x-rapidapi-host", API_HOST)
                .header("x-rapidapi-key", API_KEY)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        List<Standings> standingsList = new ArrayList<>();

        if(httpResponse.statusCode() == 200){
            String response = httpResponse.body();
            Gson gson = new Gson();
            Results results = gson.fromJson(response, Results.class);

            results.sortResponseByWinTotal();



            int rank = 1;
            for(Results.Response r : results.getResponse()){
                updateProgress(Long.valueOf(rank),Long.valueOf(results.getResults()));
                String streak = "";
                if(r.isWinStreak()){
                    streak = "W"+r.getStreak();
                }else{
                    streak = "L"+r.getStreak();
                }

                Standings standings =  new Standings();

                standings.setRank(rank);
                standings.setTeamName(r.getTeam().getName());
                standings.setW(r.getWin().getTotal());
                standings.setL(r.getLoss().getTotal());
                standings.setPct(String.format("%.3f", r.getPCT()));
                standings.setGb(Double.parseDouble(r.getGamesBehind()));
                standings.setConf(r.getConference().getWin()+"-"+r.getConference().getLoss());
                standings.setHome(r.getWin().getHome()+"-"+r.getLoss().getHome());
                standings.setAway(r.getWin().getAway()+"-"+r.getLoss().getAway());
                standings.setL10(r.getWin().getLastTen()+"-"+r.getLoss().getLastTen());
                standings.setStrk(streak);

                standingsList.add(standings);

                rank++;
            }
        }


        return standingsList;
    }
}
