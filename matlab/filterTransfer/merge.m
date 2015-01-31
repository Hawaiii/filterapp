function rgb = merge(rgbSpace,row,col)
Rr = rgbSpace(1,:);
Gr = rgbSpace(2,:);
Br = rgbSpace(3,:);

R = reshape(Rr,row,col);
G = reshape(Gr,row,col);
B = reshape(Br,row,col);
rgb(:,:,1) = R;
rgb(:,:,2) = G;
rgb(:,:,3) = B;