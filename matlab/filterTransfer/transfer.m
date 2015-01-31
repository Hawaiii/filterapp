function lap_target = transfer(M,L,A,Bm,target)
R = target(:,:,1);
G = target(:,:,2);
B = target(:,:,3);
[row,col,~] = size(target);

Rr = reshape(R,1,row*col);
Br = reshape(B,1,row*col);
Gr = reshape(G,1,row*col);
rgbSpace = [Rr;Gr;Br];

M_target = M*rgbSpace;
L_target = L*M_target;

L = log10(L_target(1,:)+0.0001);
M = log10(L_target(2,:)+0.0001);
S = log10(L_target(3,:)+0.0001);
log_target = [L;M;S];

lap_target = A*Bm*log_target;