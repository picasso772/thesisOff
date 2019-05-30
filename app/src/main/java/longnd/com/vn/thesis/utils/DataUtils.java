package longnd.com.vn.thesis.utils;

import java.util.List;

import longnd.com.vn.thesis.data.model.Customer;
import longnd.com.vn.thesis.data.model.Question;
import longnd.com.vn.thesis.data.model.ResultPsychological;

public class DataUtils {
    private static DataUtils instance;
    private Customer customer;
    private List<Question> neos;
    private List<Question> riasec;
    private List<Question> psychological;

    public ResultPsychological resultPsychological;

    public static DataUtils getInstance() {
        if (instance == null) {
            instance = new DataUtils();
        }
        return instance;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Question> getNeos() {
        return neos;
    }

    public void setNeos(List<Question> neos) {
        this.neos = neos;
    }

    public List<Question> getRiasec() {
        return riasec;
    }

    public void setRiasec(List<Question> riasec) {
        this.riasec = riasec;
    }

    public List<Question> getPsychological() {
        return psychological;
    }

    public void setPsychological(List<Question> psychological) {
        this.psychological = psychological;
    }
}
