package Algorithms;

import DataStructure.Data;
import DataStructure.Thyroid;

import java.util.*;

public class Clarans {
    ArrayList<Thyroid> current = new ArrayList<>();
    HashMap<Integer, ArrayList<Integer>> bestNode= new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> listeSomme = new HashMap<>();
    Thyroid nonMedoid,centerMedoid;
    public  double mincost = 1000000;
    double distanceMedoid;
    public Data dataset;
    HashMap<Integer, ArrayList<Integer>> hashmap = new HashMap<>();

    public Clarans(Data dataset){
        this.dataset=dataset;
    }
    HashMap<Integer, ArrayList<Integer>> getRandomNeighbor(Data data,HashMap<Integer, ArrayList<Integer>> liste,Thyroid centerMedoid){
        HashMap<Integer, ArrayList<Integer>> random=new HashMap<>();

    for (int i : liste.keySet()) {

        if (i != data.indice(centerMedoid)) random.put(i, liste.get(i));
    }
  int max = liste.get(data.indice(centerMedoid)).size();
    int randome = (int) (Math.random() * max);
    int non_medoid = liste.get(data.indice(centerMedoid)).get(randome);
    nonMedoid = data.get(non_medoid);
    random.put(non_medoid, liste.get(data.indice(centerMedoid)));

return random;
}
    public double distance( Thyroid thyroid,Thyroid th){


        return   Math.sqrt ((Math.pow(th.getDtsh() - thyroid.getDtsh(),2) + Math.pow(th.getT3() - thyroid.getT3(),2)
                + Math.pow(th.getTri() - thyroid.getTri(),2) + Math.pow(th.getTsh() - thyroid.getTsh(),2) +
                Math.pow(th.getTys() - thyroid.getTys(),2)));

    }
    public double distanceAll(Data data,HashMap<Integer,ArrayList<Integer>> listeAll){
        double dist=0;
        for(int j:listeAll.keySet()) {
            for (int i = 0; i < listeAll.get(j).size(); i++) {
                dist += distance(  data.get(j),data.get(listeAll.get(j).get(i)));

            }
        }
        return  dist;
    }
    ArrayList<Thyroid> initCluster(Data data,int numberOfClusetr){
        ArrayList<Thyroid>liste=new ArrayList<>();

        for (int j = 0; j < numberOfClusetr; j++) {
            int med = (int) (Math.random() * data.getLenght() - 1);
            if (!liste.contains(data.get(med)))
                liste.add(data.get(med));
            else j--;
        }

return liste;
}
    HashMap<Integer, ArrayList<Integer>> getDistance(Data data,int numberOfClusetr,ArrayList<Thyroid> liste) {
        HashMap<Integer, ArrayList<Integer>> listeSomme1 = new HashMap<>();
    for (int k = 0; k < data.getLenght(); k++) {
        Thyroid thyroid = data.get(k);
      ArrayList<Double>  listeSum1 = new ArrayList<Double>();
        for (int k1 = 0; k1 < numberOfClusetr; k1++) {
            Thyroid medoidCenter = liste.get(k1);
            double distance = distance( thyroid,medoidCenter);

            listeSum1.add(distance);     }
        Double min = Collections.min(listeSum1);

        Thyroid t = data.get(listeSum1.indexOf(min));
        int indice = data.indice(t);
        int index = data.indice(liste.get(indice));
        if (!listeSomme1.containsKey(index)) {
            ArrayList<Integer> e=new ArrayList<>();
            e.add(k);
            listeSomme1.put(index, e);
        } else {
            listeSomme1.get(index).add(k);
        }
    }

return  listeSomme1;
    }
   public  HashMap<Integer, ArrayList<Integer>> Clarans(Data data, int numberOfClusetr, int maxNeighbors, int numLocal) {

        for (int i = 0; i < numLocal; i++) {
        current=initCluster(data,numberOfClusetr);
        listeSomme=getDistance(data,numberOfClusetr,current);
        Iterator iterator = listeSomme.entrySet().iterator();
     //   System.out.println("***************************** iteration num local"+i+"****************************************");
       // while (iterator.hasNext()) {
         //       Map.Entry mapentry = (Map.Entry) iterator.next();
           //     System.out.println("cle " + mapentry.getKey() + " values " + mapentry.getValue());
        //}

        int j = 1;
        int rand=(int)(Math.random()*current.size());
      int indice=data.indice(current.get(rand));
        centerMedoid = data.get(indice);
        distanceMedoid=distanceAll(data,listeSomme);
            while(true){
                hashmap=getRandomNeighbor(data,listeSomme,centerMedoid);
             double  distance=distanceAll(data,hashmap);
                if(distanceMedoid>distance){
      //          System.out.println(" distance "+distance+" distancemedoidcenter "+distanceMedoid);

                hashmap.put(data.indice(nonMedoid),listeSomme.get(centerMedoid));
                for(int a:listeSomme.keySet()){
                    if(a!=data.indice(centerMedoid)) hashmap.put(a,listeSomme.get(a));
                }


                current.clear();
                centerMedoid=nonMedoid;
                distanceMedoid=distance;
                nonMedoid=null;
                    for (int a : hashmap.keySet()) {
                        current.add(data.get(a));
                    }

                listeSomme.clear();
                hashmap.clear();
                listeSomme=getDistance(data,numberOfClusetr,current);

                    j=1;
                }
                else{
                j++;
                if (j>maxNeighbors){
                break;
                                    }
                            }

                     }
            if(distanceMedoid<mincost){
                bestNode.clear();
                bestNode.putAll(listeSomme);

                mincost=distanceMedoid;
            }

        }

    Iterator iterator1 = bestNode.entrySet().iterator();
  //   System.out.println("***************bestnode**********************************");
    //  while (iterator1.hasNext()) {
      //  Map.Entry mapentry = (Map.Entry) iterator1.next();
        //System.out.println("cle " + mapentry.getKey() + " values " + mapentry.getValue());
    //}
return bestNode;
    }
    public HashMap<Integer, ArrayList<Thyroid>> clustersClarans(Data data,HashMap<Integer, ArrayList<Integer>> clust){

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

            distanceMedoid= distanceAll(data, liste);


        return distanceMedoid;

    }

}