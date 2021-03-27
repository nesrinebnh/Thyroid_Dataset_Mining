package DataStructure;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import DataStructure.Data;
import DataStructure.Thyroid;

public class Apriori {
    private Data data;
    private ArrayList<Thyroid> des_dataset;
    private HashMap<String , Table> C;
    public HashMap<String , Table> L;
    private ArrayList<Rule> rules;
    public static int nb_items = 4;
    //-------------------------CONSTRUCTEUR --------------------------
    public Apriori(Data data){
        this.data = data;
        des_dataset = new ArrayList<>();
        this.C = new HashMap<>();
        this.L = new HashMap<>();
    }
    //------------------------GEERER LES COMBINAISON DES REGLES ----------------------
    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    private void combinationUtil(ArrayList<Integer> arr, int data[], int start, int end, int index, int r){
        ArrayList<Rule> rules = new ArrayList<>();
        ArrayList<Integer> left = new ArrayList<>();
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            for (int j=0; j<r; j++)
                left.add(data[j]);
            this.rules.add(new Rule( new ArrayList<>(), left ));
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++) {
            data[index]= arr.get(i);
            combinationUtil(arr, data, i+1, end, index+1, r);
            // Since the elements are sorted, all occurrences of an element
            // must be together
            if(i!=end)
                while (arr.get(i).equals(arr.get(i+1)))
                    i++;
        }
        //return rules;
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    private void generateRules(ArrayList<Integer> arr, int n, int r) {
        // A temporary array to store all combination one by one
        int[] data = new int[r];

        // Print all combination using temprary array 'data[]'
        Collections.sort(arr);
        //
        //
        //  Arrays.sort(arr);
        combinationUtil(arr, data, 0, n-1, 0, r);
    }
    // ----------------------------------CALULER LA CONFIENCE ------------------------------------
    public float rule_confidence(Rule rule){

        float conf =0;

        ArrayList<Integer> r = rule.right;
        ArrayList<Integer> l = rule.left;

        Table C_left = this.L.get("L"+r.size());
        Table C_union = this.L.get("L"+(r.size()+l.size()));


        int leftSupport = C_left.GetSupportOfItem(l);
        r.addAll(l);

        int unionSupport = C_union.GetSupportOfItem(r);

        conf = (float)leftSupport/unionSupport;

        return conf;

    }
    //---------------------------GENERRE LES REGLES -------------------------------
    private ArrayList<Rule> getRules(TableItem ti) {
        this.rules = new ArrayList<>();

        ArrayList<Rule> rls = new ArrayList<>();
        int n = ti.getItem().size();
        //generer les combinaison
        for(int i=1 ; i<n; i++) {
            //le resultat de cette methode dans la variable de classe 'rules'
            //elle genere que la partie gauche de la regle
            generateRules(ti.getItem(), n, i);
        }

        //pour chaque regles ajouté la partie droite
        int size=this.rules.size();
        for(int j=0;j<size;j++){
            Rule r = this.rules.get(j);
            ArrayList<Integer> left = r.left;

            ArrayList<Integer> right = new ArrayList<>();
            for(int i=0; i<n; i++){
                if (!left.contains(ti.getItem().get(i)))
                    right.add(ti.getItem().get(i));
            }
            rls.add(new Rule(right,left));
        }

        return rls;

    }

    public HashMap<Rule , Float> calculate_confs(Table C){
        HashMap<Rule , Float> conf = new HashMap<>();
        for(TableItem ti: C.table){

            ArrayList<Rule> rules = getRules(ti);
            for(Rule rule :rules){
                float c = rule_confidence(rule);
                //si la confiance est negative donc une des regles nexiste pas
                if(c > 0.0 && c<=1.0)
                    conf.put(rule,c);
            }
        }
        return conf;
    }
//---------------------------------------- FONCTION DAIDE POUR LA ESCRITISATION -------------------------------
    private HashMap<Float , Integer> bining(String attribute, int bins){
        HashMap<Float , Integer> t =  new HashMap<>();
        float min_x = data.min(attribute), max_x = data.max(attribute);
        float step = (max_x - min_x) / bins;
        for(float i=min_x ; i<max_x-step ; i+=step) {
            if(i!=max_x-step){
                // if(countValues(attribute,i,i+step,false)>0){
                t.put(i, nb_items);
                nb_items++;
                // }

            }else{
                // if(countValues(attribute,i,i+step,true)>0){
                t.put(i, nb_items);
                nb_items++;
                //}

            }

        }
        return t;
    }
    private HashMap<Float , Integer> codage(String attribute){
        ArrayList<Thyroid> dataset = data.dataset;
        HashMap<Float , Integer> itm =  new HashMap<>();
        float value;
        for(Thyroid t : dataset){
            value = t.getAttributeValue(attribute);
            if(!itm.containsKey(value)){
                itm.put(value, nb_items);
                nb_items++;
            }
        }
        return itm;
    }
    private float getRightVal(HashMap<Float , Integer> attribute,float value){
        float val=-1;
        for(Float key : attribute.keySet()){
            if(value >= key)
                val = attribute.get(key);
        }
        return val;
    }
    // -------------- DESBINING METHODE
    public ArrayList<Thyroid> descritizationUsingBining(int bins){
        ArrayList<Thyroid> dataset = data.dataset;
        HashMap<Float , Integer> TSH, DTSH, T3, TYS, TRI;
        TSH =bining(Data.TSH,bins);
        DTSH =bining(Data.DTSH,bins);
        TRI =bining(Data.TRI,bins);
        T3 =bining(Data.T3,bins);
        TYS =bining(Data.TYS,bins);
        for(Thyroid t : dataset){
            float classe = t.getClasse();
            float tys = this.getRightVal(TYS,t.getAttributeValue(Data.TYS));
            float tsh = this.getRightVal(TSH,t.getAttributeValue(Data.TSH));
            float dtsh = this.getRightVal(DTSH,t.getAttributeValue(Data.DTSH));
            float tri = this.getRightVal(TRI,t.getAttributeValue(Data.TRI));
            float t3 = this.getRightVal(T3,t.getAttributeValue(Data.T3));
            des_dataset.add(new Thyroid(classe,t3, tys, tri, tsh, dtsh));
        }
        return des_dataset;
    }
    //----------------------SIMPLE DIS
    public ArrayList<Thyroid> descritizationUsingSimpleCoding(){
        ArrayList<Thyroid> dataset = data.dataset;
        HashMap<Float , Integer> TSH, DTSH, T3, TYS, TRI;
        T3 =codage(Data.T3);
        TYS =codage(Data.TYS);
        TRI =codage(Data.TRI);
        TSH =codage(Data.TSH);
        DTSH =codage(Data.DTSH);

        for(Thyroid t : dataset){
               float classe = t.getClasse();
            float tys = this.getRightVal(TYS,t.getAttributeValue(Data.TYS));
            float tsh = this.getRightVal(TSH,t.getAttributeValue(Data.TSH));
            float dtsh = this.getRightVal(DTSH,t.getAttributeValue(Data.DTSH));
            float tri = this.getRightVal(TRI,t.getAttributeValue(Data.TRI));
            float t3 = this.getRightVal(T3,t.getAttributeValue(Data.T3));
            des_dataset.add(new Thyroid(classe,t3, tys, tri, tsh, dtsh));
        }
        return des_dataset;
    }
//----------------------------------------------------
    public Table apriori_algorithm(int min_supp){


        int k=1;

        Table C = new Table(des_dataset);
        this.C.put("C"+k , C);

        Table L = C.getL(min_supp);
        this.L.put("L"+k , L);

        while(true){

            if( L.table.isEmpty() )
                break;

            k++;

            C = L.generateC(k);
            this.C.put("C"+k , C);

            L = C.getL(min_supp);
            this.L.put("L"+k , L);

        }

        Table Lk_1 = this.L.get("L"+(k-1));

        return Lk_1;
    }
    private void writeToFile(String fileName, Table t){
        try {
            FileWriter myWriter = new FileWriter(fileName);
            for(TableItem ti : t.table)
                myWriter.write(ti.toString()+"\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public ArrayList<Thyroid> getDes_dataset() {
        return des_dataset;
    }
}
