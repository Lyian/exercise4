package de.hhu.propra.exercise4.repository.helpers;

public class QueryHelper {
    public static String createLikeParam(String param){
        return "%"+param+"%";
    }
}
