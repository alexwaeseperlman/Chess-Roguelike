package Game; 
  
interface Move {
  boolean allowed(Piece p, Map m);
  Piece outcome(Piece p, Map m);
}

abstract class Piece {
  // Global positions
  int x, y;

  Move[] moves;
}