<h1>fafachess</h1>
fafachess is a chess engine written for learning purposes, using Scala and Akka.
The main focus is put on readability, testability and clean design.

fafachess supports UCI protocol, making it easy to play against it using any of the popular GUI's like <a href="www.playwitharena.com">Arena Chess GUI</a>.


<h1>Features</h1>
<ul>
<li>UCI protocol support</li>
<li>support for all allowed moves including castling, en passant, and promotion.
However, for efficiency reasons, under-promotion is not considered when searching for best move.</li>
<li>best move search using simple negamax algorithm (will probably add alpha-beta pruning in the nearest future).</li>
<li>evaluation based on material and <a href="https://chessprogramming.wikispaces.com/Piece-Square+tables">piece-square tables.</a></li>
</ul>
