package appPackage.al3ra8e.icart.util;

import android.net.Uri;

import java.util.Map;
import java.util.Map.Entry;

public class UrlBuilder {
    String url ;

    public UrlBuilder(){}

    public UrlBuilder(String url){

        this.url = url ;

    }

    public String getUrl() {

        return url;

    }

    public Uri getURI(){
        return Uri.parse(url) ;
    }

    public UrlBuilder setUrl(String url) {

        this.url = url;
        return this ;

    }
    public UrlBuilder setUrlParameter(Map<String , String> params){
        url+="?" ;

        for (Entry<String , String > entry : params.entrySet()) {
            url += entry.getKey()+"="+entry.getValue()+"&" ;
        }
        url = url.substring(0 , url.length()-1) ;

        return this ;
    }

    public UrlBuilder setUrlParameter(String [] keys , String [] values){

        url+="?" ;

        for (int i = 0; i < keys.length; i++) {
            url+= keys[i]+"="+values[i]+"&" ;
        }

        url = url.substring(0 , url.length()-1) ;

        return this ;

    }

    public UrlBuilder setUrlParameter(String key , String value){

        url+="?"+key+"="+value ;
        return this ;

    }

    public UrlBuilder setUrlPath(String path){
        url+=path;
        return this ;

    }

}
