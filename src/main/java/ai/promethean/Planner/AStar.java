package ai.promethean.Planner;

import java.util.*;

public class AStar {
   public static void main(String[] args) {
    /*
    Setup
     */
    node chi = new node("chi",375);
    node cle = new node("cle",150);
    node ind = new node("ind",200);
    node col = new node("col",0);
    node det = new node("det",350);
    Edge ChiDet = new Edge(det,283);
    Edge ChiCle = new Edge(cle,345);
    Edge ChiInd = new Edge(ind,182);
    Edge CleChi = new Edge(chi,345);
    Edge CleDet = new Edge(det,169);
    Edge CleCol = new Edge(col,144);
    Edge IndChi = new Edge(chi,182);
    Edge IndCol = new Edge(col,176);
    Edge ColInd = new Edge(ind,176);
    Edge ColCle = new Edge(cle,144);
    Edge DetChi = new Edge(chi,283);
    Edge DetCle = new Edge(cle,169);

    List<Edge> chiList = new ArrayList<Edge>();
    chiList.addAll(Arrays.asList(ChiDet,ChiCle,ChiInd));
    chi.setNeighbors(chiList);
    List<Edge> cleList = new ArrayList<Edge>();
    cleList.addAll(Arrays.asList(CleChi,CleDet,CleCol));
    cle.setNeighbors((cleList));
    List<Edge> indList = new ArrayList<Edge>();
    indList.addAll(Arrays.asList(IndChi,IndCol));
    ind.setNeighbors((indList));
    List<Edge> colList = new ArrayList<Edge>();
    colList.addAll(Arrays.asList(ColInd,ColCle));
    col.setNeighbors((colList));
    List<Edge> detList = new ArrayList<Edge>();
    detList.addAll(Arrays.asList(DetChi,DetCle));
    det.setNeighbors((detList));

       AStar(chi,col);
       List<String> path = printPath(col);
       System.out.print("Path: "+path);

   }
   public static List<String> printPath(node finalNode) {
       List<String> path = new ArrayList<String>();
       for(node current = finalNode;current!=null; current = current.parent) {
           path.add(current.name);
       }
       Collections.reverse(path);
       return path;
   }
   public static void AStar(node startState, node endState) {
       Set<node> explored = new HashSet<node>();

       PriorityQueue<node> queue = new PriorityQueue<node>(7,
               new Comparator<node>() {
                   @Override
                   public int compare(node o1, node o2) {
                       if(o1.fVal > o2.fVal) {
                           return 1;
                       }
                       else if(o1.fVal < o2.fVal) {
                           return -1;
                       } else {
                           return 0;
                       }
                   }
               });
       startState.gVal = 0;
       queue.add(startState);
       boolean found = false;
       while(!queue.isEmpty() && !found) {
           //the node in the queue with the lowest f score
           node current = queue.poll();
           explored.add(current);

           //End State Found
           if (current.name.equals(endState.name)) {
               found = true;
           }

           //check every edge of current node
           for (Edge e: current.neighbors){
               node child = e.targetNode;
               int cost = e.edgeWeight;
               int tempGval = current.gVal + cost;
               int tempFVal = tempGval + child.heuristicDist;

               // if child has been evaluated, but the fVal is higher then skip
               if (explored.contains(child) && (tempFVal >= child.fVal)) {
                   continue;
               }

               // if child node FVal is lower or not in queue
               else if(!queue.contains(child) || tempFVal < child.fVal) {
                   child.parent = current;
                   child.gVal = tempGval;
                   child.fVal = tempFVal;

                   if(queue.contains(child)) {
                       queue.remove(child);
                   }
                   queue.add(child);
               }
           }


       }
   }
}

class node {
    public String name;

    public List<Edge> neighbors;

    public int heuristicDist;

    public int fVal;

    public int gVal;

    public node parent;

    public node(String name, int dist) {
        this.name = name;
        this.heuristicDist = dist;
    }

    public void setNeighbors(List<Edge> neighbors) {
        this.neighbors = neighbors;
    }
}

class Edge {
    public node targetNode;

    public int edgeWeight;

    public Edge(node targetNode, int edgeWeight) {
        this.edgeWeight = edgeWeight;
        this.targetNode = targetNode;
    }

}