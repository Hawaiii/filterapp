function rgbSpace = split(target)
R = target(:,:,1);
G = target(:,:,2);
B = target(:,:,3);
[row,col,~] = size(target);
Rr = reshape(R,1,row*col);
Br = reshape(B,1,row*col);
Gr = reshape(G,1,row*col);
rgbSpace = [Rr;Br;Gr];
return