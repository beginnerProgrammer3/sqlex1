package sqlchallenge.sql;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sqlchallenge.sql.model.Client;


import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class SqlApplication {

    public SqlApplication(){
    }

    public static void main(String[] args) {
        SpringApplication.run(SqlApplication.class, args);

        Solution();

        System.exit(0);

    }

    private static void Solution() {
        JSONParser jsonParser = new JSONParser();

        //read data from file
        try (FileReader readData = new FileReader("statuses.json")) {
            Object file = jsonParser.parse(readData);
            JSONObject object = (JSONObject) file;

            JSONArray clientId = (JSONArray) object.get("records");

            List<Client> clientList = new ArrayList<>();
            List<Client> sortedClients = new ArrayList<>();

            //adding data to array of client objects
            for(int i=0; i<clientId.size();i++){
                JSONObject clientToSave = (JSONObject) clientId.get(i);


                Client clientToAdd = new Client(
                        (Long)clientToSave.get("klient_id"),
                        (Long)clientToSave.get("pracownik_id"),
                        (Long)clientToSave.get("kontakt_id"),
                        Timestamp.valueOf((String)clientToSave.get("kontakt_ts")),
                        (String)clientToSave.get("status"));
                //fastest sol to compare date with date in challenge 1
                if (clientToAdd.getKontakt_ts().after(Timestamp.valueOf("2017-07-01 00:00:00.0"))){
                    clientList.add(clientToAdd);
                }

                // sorting table by Klient_Id then by kontakt_ts
                Comparator<Client> compareByKlient_IDthenKontakt_Ts = Comparator
                        .comparing(Client::getKlient_id).thenComparing(Client::getKontakt_ts);

                sortedClients = clientList.stream()
                                .sorted(compareByKlient_IDthenKontakt_Ts)
                                .collect(Collectors.toList());
            }
            //line for output test only, i commented it
            sortedClients.stream().forEach(clientToShow -> System.out.println(clientToShow.toString()));

            //write to csv execution
            writeToCSV((ArrayList<Client>) sortedClients);



        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //final csv separator
    private static final String CSV_SEPARATOR = ",";

    //method to save file
    private static void writeToCSV(ArrayList<Client> clientList)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("result.csv"), "UTF-8"));
            //added structure tag/names
            StringBuffer oneLine = new StringBuffer();
            oneLine.append("kontakt_id");
            oneLine.append(CSV_SEPARATOR);
            oneLine.append("klient_id");
            oneLine.append(CSV_SEPARATOR);
            oneLine.append("pracownik_id");
            oneLine.append(CSV_SEPARATOR);
            oneLine.append("status");
            oneLine.append(CSV_SEPARATOR);
            oneLine.append("kontakt_ts");
            bw.write(oneLine.toString());
            bw.newLine();
            //adding each row to csv
            for (Client client : clientList)
            {
                oneLine = new StringBuffer();
                oneLine.append(client.getKontakt_id());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(client.getKlient_id());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(client.getPracownik_id());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(client.getStatus());
                oneLine.append(CSV_SEPARATOR);

                //cut the .0 from timestamp(fastest solution for me)
                String timeStampWithZero = client.getKontakt_ts().toString();
                timeStampWithZero = timeStampWithZero.substring(0, timeStampWithZero.indexOf('.'));
                oneLine.append(timeStampWithZero);

                //writing in one line
                bw.write(oneLine.toString());
                bw.newLine();
            }
            //save csv
            bw.flush();
            //close form file
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}
