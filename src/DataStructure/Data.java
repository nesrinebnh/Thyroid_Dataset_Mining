package DataStructure;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import DataStructure.Thyroid;

public class Data {

    public static String T3 = "t3";
    public static String TRI = "tri";
    public static String TSH = "tsh";
    public static String TYS = "tys";
    public static String DTSH = "dtsh";

    public static String PATH = "./src/FXML/Tyro_test.txt";
    public ArrayList<Thyroid> dataset;
    private int lenght;

    public static int c1 = 150;
    public static int c2 = 35;
    public static int c3 = 30;

    public int indice(Thyroid thyroid){
        for (int i=0;i<dataset.size();i++){
            if (dataset.get(i).equals(thyroid)) return i;
        }
        return -1;
    }

    public Thyroid get(int i){
        return dataset.get(i);
    }

    public int getLenght(){
        return lenght;
    }




    public ArrayList<Thyroid> getDataset() {
		return dataset;
	}

	public Data(String path) {

        dataset = readFile(path);
        lenght = dataset.size();

    }

    public Data() {
        dataset = readFile("FXML/Thyroid_Dataset.txt");
        lenght = dataset.size();

    }


    public float median(String attributeName) {
        //sorting the data :
        Collections.sort(dataset, new Comparator<Thyroid>() {
            @Override
            public int compare(Thyroid o1, Thyroid o2) {
                if(o1.getAttributeValue(attributeName) > o2.getAttributeValue(attributeName))
                    return 1;
                if(o1.getAttributeValue(attributeName) < o2.getAttributeValue(attributeName))
                    return -1;
                return 0;
            }
        });



        if (lenght % 2 == 0) {
            //the lenght is even so we take the avg between values in lenght/2 and lenght/2-1
            float med = dataset.get((int) (lenght / 2)).getAttributeValue(attributeName) +
                    dataset.get((int) (lenght / 2) - 1).getAttributeValue(attributeName);
            return med / 2;
        } else
            //the lenght is odd so we take the value is pos : lenght/2 (without adding 1 cuz we start from 0)
            return dataset.get((int) (lenght / 2)).getAttributeValue(attributeName);
    }

    public float Q1(String attributeName) {
        float q1;

        if (lenght % 4 == 0) {

            q1 = dataset.get((int) (lenght / 4) - 1).getAttributeValue(attributeName);

        } else {   q1= dataset.get((int) (lenght / 4) ).getAttributeValue(attributeName); }
        return q1;

    }


    public float Q3(String attributeName) {
        float q3;
        if ((3*lenght) % 4 == 0) {

            q3 = dataset.get((int) (3*lenght / 4) - 1).getAttributeValue(attributeName);

        } else {   q3= dataset.get((int) (3*lenght / 4) ).getAttributeValue(attributeName); }
        return q3;

    }

    public float Min(String attributeName)
    {
        return (dataset.get((int) 0).getAttributeValue(attributeName));
    }

    public float Max(String attributeName)
    {
        return (dataset.get((int) lenght -1 ).getAttributeValue(attributeName));
    }



    public ArrayList<Float> mode(String attributeName) {
        ArrayList<Float> mode = new ArrayList<>();
        //i create a map <attribute value , freq>
        // then ill take the max freq attribute value as the mode
        // isn case we have more then one ill return an array !
        HashMap<Float, Integer> map = new HashMap<>();
        for (Thyroid t : dataset) {
            if (map.containsKey(t.getAttributeValue(attributeName)))
                map.replace(t.getAttributeValue(attributeName),
                        map.get(t.getAttributeValue(attributeName)) + 1
                );
            else
                map.put(t.getAttributeValue(attributeName), 1);
        }
        //sorting the map by values in sortedMap
        List<Map.Entry<Float, Integer>> sortedMap = new ArrayList<>(map.entrySet());
        Collections.sort(sortedMap, new Comparator<Map.Entry<Float, Integer>>() {
            public int compare(Map.Entry<Float, Integer> o1,
                               Map.Entry<Float, Integer> o2) {

                if(o1.getValue() < o2.getValue())
                    return 1;
                if(o1.getValue() > o2.getValue())
                    return -1;
                return 0;
            }
        });

        int max = sortedMap.get(0).getValue();
        for (Map.Entry<Float, Integer> line : sortedMap) {
            if (max == line.getValue())
                mode.add(line.getKey());
            else break;
        }
        return mode;
    }

    public boolean isSymetric(String attributeName) {

        float mean = mean(attributeName);
        ArrayList<Float> mode = mode(attributeName);
        float modeValue = mode.get(0);
        float median = median(attributeName);

        return (median == mean && mean == modeValue);
    }

    public boolean isSymetric(float mode, float median, float mean) {

        return (median == mean && mean == mode);
    }

    public String modeType(String attributeName) {
        ArrayList<Float> mode = mode(attributeName);
        switch (mode.size()) {
            case 1:
                return "Le mode est unimodal";
            case 2:
                return "Le mode est bimodal";
            default:
                return "Le mode est multimodal";
        }
    }

    public String modeType(ArrayList<Float> mode) {
        switch (mode.size()) {
            case 1:
                return "Le mode est unimodal";
            case 2:
                return "Le mode est bimodal";
            default:
                return "Le mode est multimodal";
        }
    }

    public float max(String attributeName) {
        float max = Collections.max(dataset, new Comparator<Thyroid>() {
            @Override
            public int compare(Thyroid o1, Thyroid o2) {
                if(o1.getAttributeValue(attributeName) > o2.getAttributeValue(attributeName))
                    return 1;
                if(o1.getAttributeValue(attributeName) < o2.getAttributeValue(attributeName))
                    return -1;
                return 0;
            }
        }).getAttributeValue(attributeName);
        return max;
    }

    public float min(String attributeName) {
        float min = Collections.min(dataset, new Comparator<Thyroid>() {
            @Override
            public int compare(Thyroid o1, Thyroid o2) {
                if(o1.getAttributeValue(attributeName) > o2.getAttributeValue(attributeName))
                    return 1;
                if(o1.getAttributeValue(attributeName) < o2.getAttributeValue(attributeName))
                    return -1;
                return 0;
            }
        }).getAttributeValue(attributeName);
        return min;
    }

    /*public ArrayList<Integer> classDistribution() {
        ArrayList<Integer> dist = new ArrayList<>();
        //we have 3 classes 1, 2 and 3 so :
        //dist[0] contains the freq of class 1
        //dist[1] contains the freq of class 2
        //dist[2] contains the freq of class 3
        dist.add(0);
        dist.add(0);
        dist.add(0);
        for (Thyroid t : dataset) {
            dist.set(t.getClasse() - 1, dist.get(t.getClasse() - 1) + 1);
        }
        return dist;
    }*/

    public HashMap<String, String> attributeDescription(String attributeName) {
        HashMap<String, String> desc = new HashMap<>();
        desc.put("Nom abrivé  d'attribut : ", attributeName.toUpperCase());
        desc.put("La valeur max : ", "" + max(attributeName));
        desc.put("La valeur min : ", "" + min(attributeName));
        desc.put("Type : ", "attribut numérique continu");

        switch (attributeName) {
            case "t3":
                desc.put("Description d'attribut : ", "Test d’absorption de la résine T3, donnée comme un pourcentage");
                break;
            case "tri":
                desc.put("Description d'attribut : ", "Triiodothyronine sérique totale mesurée par dosage radio-immunologique");
                break;
            case "tsh":
                desc.put("Description d'attribut : ", "Hormone basale de stimulation de la thyroïde telle que mesurée par dosage radio-immunologique");
                break;
            case "tys":
                desc.put("Description d'attribut : ", "Thyroxine sérique totale mesurée par la méthode de déplacement isotopique");
                break;
            case "dtsh":
                desc.put("Description d'attribut : ", "Différence absolue maximale de la valeur TSH après injection de 200 micro grammes d’hormone de libération de la thyrotropine par rapport à la valeur basale");
                break;
            default:
                break;
        }
        return desc;
    }

    public HashMap<String, String> classDescription() {
        HashMap<String, String> desc = new HashMap<>();
        desc.put("Nom d'attribut : ", "Classe");
        desc.put("Type : ", "attribut nominal");
        desc.put("Le nombre de Classes : ", "3");
        desc.put("La description des Classes : ", "1 : Nomrmale(euthyroidie)\n2 : Hyper(hyperthyroidie)\n3 : Hypo (hypothyroidie)");
        return desc;
    }

    public ArrayList<Thyroid> readFile(String filePath) {
        try {
            ArrayList<Thyroid> dataset = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream(filePath)
					));
            String line;
            List<String> objectLine = new ArrayList<>();
            Thyroid object;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                objectLine = Arrays.asList(line.split(","));

                int classe = Integer.valueOf(objectLine.get(0));
                float t3 = Float.valueOf(objectLine.get(1));
                float tys = Float.valueOf(objectLine.get(2));
                float tri = Float.valueOf(objectLine.get(3));
                float tsh = Float.valueOf(objectLine.get(4));
                float dtsh = Float.valueOf(objectLine.get(5));

                object = new Thyroid(classe, t3, tys, tri, tsh, dtsh);
                //System.out.println(object.toString());
                dataset.add(object);
            }
            reader.close();
            return dataset;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filePath);
            e.printStackTrace();
            return null;
        }
    }

    /**************************************** Histograms ******************************************/
    /**********************************************************************************************/
    private int countValues(String attribute, float value, int start){
        int count=0;
        int i = start;
        while(i<lenght){
            if(dataset.get(i).getAttributeValue(attribute)<=value) count++;
            else break;
            i++;
        }

        return count;
    }

    public ArrayList<Point> makeHistogram(String attribute , int bins){
        float min_x = this.min(attribute), max_x = this.max(attribute);
        float step = (max_x - min_x) / bins;
        int i=0;
        float x = min_x;
        int y=0;

        /*System.out.println(min_x);
        System.out.println(step);
        System.out.println(max_x);*/

        ArrayList<Point> points = new ArrayList<>();
        //trier lattribut
        this.Trier_Data(attribute);

        while(i<this.lenght){
            y=countValues(attribute,x+step, i);
            points.add(new Point(x,y));
            x += step;
            i +=y;
        }
        return points;
    }

    public void min_max_normalization(float new_min , float new_max){
        float new_val;
        for (Thyroid t : this.dataset){
            new_val = ((t.getAttributeValue(this.DTSH) - this.min(this.DTSH)) / (this.max(this.DTSH) - this.min(this.DTSH)))*(new_max-new_min) + new_min;
            t.setDtsh(new_val);
            new_val = ((t.getAttributeValue(this.T3)-this.min(this.T3)) / (this.max(this.T3) - this.min(this.T3)))*(new_max-new_min) + new_min;
            t.setT3(new_val);
            new_val = ((t.getAttributeValue(this.TRI)-this.min(this.TRI)) / (this.max(this.TRI) - this.min(this.TRI)))*(new_max-new_min) + new_min;
            t.setTri(new_val);
            new_val = ((t.getAttributeValue(this.TSH)-this.min(this.TSH)) / (this.max(this.TSH) - this.min(this.TSH)))*(new_max-new_min) + new_min;
            t.setTsh(new_val);
            new_val = ((t.getAttributeValue(this.TYS)-this.min(this.TYS)) / (this.max(this.TYS) - this.min(this.TYS))) *(new_max-new_min) + new_min;
            t.setTys(new_val);
        }
    }

    public void z_score_normalization(){
        float new_val;
        for (Thyroid t : this.dataset){
            new_val = (t.getAttributeValue(this.DTSH)-this.mean(this.DTSH))/this.standard_deviation(this.DTSH);
            t.setDtsh(new_val);
            new_val = (t.getAttributeValue(this.T3)-this.mean(this.T3))/this.standard_deviation(this.T3);
            t.setT3(new_val);
            new_val = (t.getAttributeValue(this.TRI)-this.mean(this.TRI))/this.standard_deviation(this.TRI);
            t.setTri(new_val);
            new_val = (t.getAttributeValue(this.TSH)-this.mean(this.TSH))/this.standard_deviation(this.TSH);
            t.setTsh(new_val);
            new_val = (t.getAttributeValue(this.TYS)-this.mean(this.TYS))/this.standard_deviation(this.TYS);
            t.setTys(new_val);
        }
    }

    public float mean(String attributeName) {
        float mean = 0;
        for (Thyroid t : dataset)
            mean = mean + t.getAttributeValue(attributeName);

        mean = mean / lenght;
        return mean;
    }

    public float standard_deviation(String attributeName){
        float std = 0;
        for (Thyroid t : dataset)
            std = std + (float) Math.pow((t.getAttributeValue(attributeName) - this.mean(attributeName)),2);

        std = std / this.lenght;
        return (float) Math.sqrt(std);
    }
    public void Trier_Data(String attributeName)
    {
        //sorting the data :
        Collections.sort(dataset, new Comparator<Thyroid>() {
            @Override
            public int compare(Thyroid o1, Thyroid o2) {
                if(o1.getAttributeValue(attributeName) > o2.getAttributeValue(attributeName))
                    return 1;
                if(o1.getAttributeValue(attributeName) < o2.getAttributeValue(attributeName))
                    return -1;
                return 0;
            }
        });

    }


    public ArrayList<Thyroid> getThyroidsClass(int classe){
        ArrayList<Thyroid> listeClasse = new ArrayList<>();
        for (Thyroid t : dataset){
            if (t.getClasse()==classe)
                listeClasse.add(t);
        }
        return listeClasse;
    }




//////////////////////digrammme//////////////////////////
public ArrayList<Point> makeDigramme( String att,String att2){
        ArrayList<Point> values=new ArrayList<Point>();
        for (int i=0;i<dataset.size();i++)
        values.add(new Point(dataset.get(i).getAttributeValue(att),dataset.get(i).getAttributeValue(att2)));

    return values;
}


}
