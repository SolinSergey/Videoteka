package ru.gb.catalogservice.utils;

import lombok.Data;
import ru.gb.catalogservice.exceptions.IncorrectFilterParametrException;

import java.util.List;

@Data
public class DirectorsFilter {
    private String[] filterDirectorFirstName;
    private String[] filterDirectorLastName;

    public DirectorsFilter(String[] filterDirectorList) {
        this.filterDirectorFirstName = new String[filterDirectorList.length];
        this.filterDirectorLastName = new String[filterDirectorList.length];
        prepareFilterDirector(filterDirectorList);
    }
    public DirectorsFilter(List<String> filterDirectorList) {
        //this(convertListToArray(filterDirectorList));
        this(filterDirectorList.toArray(new String[0]));
    }
    private static String[] convertListToArray(List<String> list){
        String[] filterDirectorArray = new String[list.size()];
        for (int i=0;i<list.size();i++){
            filterDirectorArray[i]=list.get(i);
        }
        return filterDirectorArray;
    }

    public void prepareFilterDirector(String[] filterDirectorList){
        for (int i=0;i< filterDirectorList.length;i++){
            String[] s=filterDirectorList[i].split(" ");
            if (s.length==2){
                filterDirectorFirstName[i]=s[0];
                filterDirectorLastName[i]=s[1];
            }else if(s.length>2){
                filterDirectorFirstName[i]=s[0];
                filterDirectorLastName[i]="";
                for (int ii=1;ii<s.length;ii++){
                    if (ii!=1){
                        filterDirectorLastName[i]=filterDirectorLastName[i]+" ";
                    }
                    filterDirectorLastName[i]=filterDirectorLastName[i]+s[ii];
                }
            }else{
                throw new IncorrectFilterParametrException("Некорректный параметр фильтра");
            }
        }
    }
}
