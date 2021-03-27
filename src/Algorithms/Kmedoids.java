package Algorithms;
import DataStructure.*;

import java.util.*;


public class Kmedoids {
    ArrayList<Integer> listeofMedoids = new ArrayList<>();
    HashMap<Integer, ArrayList<Integer>> hashmap = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> listeSomme = new HashMap<>();
    public Data dataset;

    public Kmedoids(Data dataset) {
        this.dataset = dataset;
    }
    public double distance(Thyroid thyroid, Thyroid th) {
        return Math.sqrt((Math.pow(th.getDtsh() - thyroid.getDtsh(), 2) + Math.pow(th.getT3() - thyroid.getT3(), 2)
                + Math.pow(th.getTri() - thyroid.getTri(), 2) + Math.pow(th.getTsh() - thyroid.getTsh(), 2) +
                Math.pow(th.getTys() - thyroid.getTys(), 2)));

    }
    public double distanceAll(Data data, Thyroid th, ArrayList<Integer> listeAll) {
        double dist = 0;
        for (int i = 0; i < listeAll.size(); i++) {

          dist+=distance( th,data.get(listeAll.get(i)));

        }
        return dist;
    }
    ArrayList<Integer> initCluster(Data data,int numberOfClusetr){
        ArrayList<Integer>liste=new ArrayList<>();

        for (int j = 0; j < numberOfClusetr; j++) {
            int med = (int) (Math.random() * data.getLenght() - 1);
            if (!liste.contains(med))
                liste.add(med);
            else j--;
        }
        return liste;
    }
    HashMap<Integer, ArrayList<Integer>> getDistance(Data data,ArrayList<Integer> liste) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int k = 0; k < data.getLenght(); k++) {
            Thyroid thyroid = data.get(k);
            ArrayList<Double>  listeSum1 = new ArrayList<Double>();
            for (int i = 0; i < liste.size(); i++) {
                Thyroid medoidCenter =data.get(liste.get(i));
                double distance = distance( thyroid,medoidCenter);
                listeSum1.add(distance);
            }
            Double min = Collections.min(listeSum1);
            if (!map.containsKey(liste.get(listeSum1.indexOf(min)))) {
                ArrayList<Integer> e=new ArrayList<>();
                e.add(k);
                map.put(liste.get(listeSum1.indexOf(min)),e);
            } else {
                map.get(liste.get(listeSum1.indexOf(min))).add((k));
            }
        }

        return  map;
    }


    public HashMap<Integer, ArrayList<Integer>> fit(Data data, int numberOfClusetr) {
        listeofMedoids=initCluster(data,numberOfClusetr);
      //  for(int i=0;i<listeofMedoids.size();i++){
        //    System.out.println(listeofMedoids.get(i));
        //}
        //int cpt = 0;
        int keep;
       while (true) {
            listeSomme=getDistance(data,listeofMedoids);

           keep = 0;
          for (int k : listeSomme.keySet()) {
                double distance = 0;
                double distanceMedoid = 0;
                Thyroid centerMedoid;
                Thyroid nonMedoid;
                centerMedoid = data.get(k);
              //  System.out.println("*********************************** k : " + k + "**************************************************");

                for (int i = 0; i < listeSomme.get(k).size(); i++) {
                    int random = (int) (Math.random() * listeSomme.get(k).size());
                    int non_medoid = listeSomme.get(k).get(random);
                    nonMedoid = data.get(non_medoid);
                    if (!centerMedoid.equals(nonMedoid)) {
                        distance = distanceAll(data, nonMedoid, listeSomme.get(k));
                        distanceMedoid = distanceAll(data, centerMedoid, listeSomme.get(k));
                        if (distanceMedoid > distance) {
                            centerMedoid = nonMedoid;



                        }
                    }


                }

                hashmap.put(data.indice(centerMedoid), listeSomme.get(k));
                if (data.indice(centerMedoid) == k)
                    keep++;

            }
        //    System.out.println(keep);
            listeSomme.clear();
            listeSomme.putAll(hashmap);

            hashmap.clear();
            listeofMedoids.clear();
            for (int a : listeSomme.keySet()) {
                listeofMedoids.add(a);
            }

            if (keep == numberOfClusetr)
                break;

         }

        return listeSomme;
    }


    public HashMap<Integer, ArrayList<Thyroid>> clusters(Data data,HashMap<Integer, ArrayList<Integer>> clust){

        HashMap<Integer, ArrayList<Thyroid>> cl=new HashMap<>();

        for(int key:clust.keySet()){
            for (int j=0;j<clust.get(key).size();j++) {
                if (!cl.containsKey(key)) {
                    ArrayList<Thyroid> liste = new ArrayList<>();

                    liste.add(data.get(clust.get(key).get(j)));
                    cl.put(key,liste);
                } else {
                   cl.get(key).add(data.get(clust.get(key).get(j)));
                }
            }
        }

        return cl;
    }



    public double precision(int classe, int cluster,HashMap<Integer, ArrayList<Thyroid>> clusters) {
        //
        ArrayList<Thyroid> classList = dataset.getThyroidsClass(classe);
        int nij = 0;
        for (Thyroid t : clusters.get(cluster)) {
            if (classList.contains(t))
                nij++;
        }
        return ((double) nij) / clusters.get(cluster).size();
    }
    public double recall(int classe, int cluster,HashMap<Integer, ArrayList<Thyroid>> clusters) {
        //
        ArrayList<Thyroid> classList = dataset.getThyroidsClass(classe);
        int nij = 0;
        for (Thyroid t : clusters.get(cluster)) {
            if (classList.contains(t))
                nij++;
        }

        return ((double) nij) / classList.size();
    }

    public double fmeasure(int classe, int cluster,HashMap<Integer, ArrayList<Thyroid>> clusters) {
        double p = precision(classe, cluster,  clusters);
        double r = recall(classe, cluster,  clusters);

        if (p + r == 0) return 0;
        return ((double) (2 * p * r)) / (p + r);
    }

    public double maxfmeasure(int classe,HashMap<Integer, ArrayList<Thyroid>> clusters) {
        double maxf = 0;
        for (Map.Entry<Integer, ArrayList<Thyroid>> cluster : clusters.entrySet()) {
            double f = fmeasure(classe, cluster.getKey(),clusters);
            if (f > maxf)
                maxf = f;
        }
        return maxf;
    }
    public double fmeasure(HashMap<Integer, ArrayList<Thyroid>> clusters) {
        //get the  maxð?‘—(ð??¹(ð?‘–,ð?‘—))
        double f1 = maxfmeasure(1,clusters);
        double f2 = maxfmeasure(2,clusters);
        double f3 = maxfmeasure(3,clusters);

        return (Data.c1 * f1 + Data.c2 * f2 + Data.c3 * f3) / dataset.getLenght();
    }



     public double emeasure(Data data,HashMap<Integer,ArrayList<Integer>> liste) {
         double distanceMedoid=0;
         double dis=0;

         for (int k : liste.keySet()) {
             Thyroid centerMedoid=data.get(k);
//System.out.println(data.indice(centerMedoid)+"\t"+liste.get(k));
             distanceMedoid+= distanceAll(data, centerMedoid, liste.get(k));
//System.out.println(distanceMedoid);
for (int j=0;j<liste.get(k).size();j++) {
    int a=liste.get(k).get(j);
    data.get(a);
    dis += distance(centerMedoid,data.get(a));
    //System.out.println("dis " +dis);
}
         }
         return distanceMedoid;

    }


}
