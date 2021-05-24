package sqlchallenge.sql.model;

import java.sql.Timestamp;

public class Client {
    long klient_id;
    long pracownik_id;
    long kontakt_id;
    Timestamp kontakt_ts;
    String status;

    public Client(long klient_id, long pracownik_id, long kontakt_id, Timestamp kontakt_ts, String status) {
        this.klient_id = klient_id;
        this.pracownik_id = pracownik_id;
        this.kontakt_id = kontakt_id;
        this.kontakt_ts = kontakt_ts;
        this.status = status;
    }

    public Client(){

    }

    public long getKlient_id() {
        return klient_id;
    }

    public void setKlient_id(long klient_id) {
        this.klient_id = klient_id;
    }

    public long getKontakt_id() {
        return kontakt_id;
    }

    public void setKontakt_id(long kontakt_id) {
        this.kontakt_id = kontakt_id;
    }

    public long getPracownik_id() {
        return pracownik_id;
    }

    public void setPracownik_id(long pracownik_id) {
        this.pracownik_id = pracownik_id;
    }

    public Timestamp getKontakt_ts() {
        return kontakt_ts;
    }

    public void setKontakt_ts(Timestamp kontakt_ts) {
        this.kontakt_ts = kontakt_ts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "kontakt_id=" + kontakt_id +
                ", klient_id=" + klient_id +
                ", pracownik_id=" + pracownik_id +
                ", kontakt_ts='" + kontakt_ts + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
