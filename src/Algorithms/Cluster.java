package Algorithms;

import DataStructure.Thyroid;

import java.util.ArrayList;

public class Cluster {
    private int medoid;
    ArrayList<Integer> listeOfObject=new ArrayList<>();
    Cluster(){

    }
    Cluster(int medoid,ArrayList<Integer> listeOfObject){
        this.medoid=medoid;
        this.listeOfObject=listeOfObject;
    }
    @Override
    public String toString() {
        return "Thyroid{" +
                "medoid = " + this.medoid +
                ", listofObject = " + this.listeOfObject  +

                '}';
    }

 public void setCluster(int medoid){
     this.medoid=medoid;
 }
}
