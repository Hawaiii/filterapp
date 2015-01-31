clear all
close all
clc
Isource = imread('scenery.jpg');
Itarget = imread('warm.jpg');
% [sizex,sizey] = size(Isource);
% Itarget = imresize(Itarget,.1);
% Isource = imresize(Isource,.2);
Isource = im2double(Isource);
Itarget = im2double(Itarget);

cformLAB = makecform('srgb2lab');
% convert both images to LAB color space
IsourceLAB = applycform(Isource,cformLAB);
ItargetLAB = applycform(Itarget,cformLAB);

L_IsourceLAB= IsourceLAB(:, :, 1);  % Extract the L image.
L_sourcestd = std(L_IsourceLAB(:));
L_sourcemean = mean(L_IsourceLAB(:));
A_IsourceLAB = IsourceLAB(:, :, 2);  % Extract the A image.
A_sourcestd = std(A_IsourceLAB(:));
A_sourcemean = mean(A_IsourceLAB(:));
B_IsourceLAB = IsourceLAB(:, :, 3);  % Extract the B image.
B_sourcestd = std(B_IsourceLAB(:));
B_sourcemean = mean(B_IsourceLAB(:));

L_ItargetLAB= ItargetLAB(:, :, 1);  % Extract the L image.
L_targestd = std(L_ItargetLAB(:));
L_targemean = mean(L_ItargetLAB(:));
A_ItargetLAB = ItargetLAB(:, :, 2);  % Extract the A image.
A_targestd = std(A_ItargetLAB(:));
A_targemean = mean(A_ItargetLAB(:));
B_ItargetLAB = ItargetLAB(:, :, 3);  % Extract the B image.
B_targestd = std(B_ItargetLAB(:));
B_targemean = mean(B_ItargetLAB(:));

% subtract the means from the target image
L_ItargetLAB = L_ItargetLAB-L_targemean;
A_ItargetLAB = A_ItargetLAB-A_targemean;
B_ItargetLAB = B_ItargetLAB-B_targemean;

% scale and add the source mean
L_ItargetLAB = (L_targestd/L_sourcestd)*L_ItargetLAB+L_sourcemean;
A_ItargetLAB = (A_targestd/A_sourcestd)*A_ItargetLAB+A_sourcemean;
B_ItargetLAB = (B_targestd/B_sourcestd)*B_ItargetLAB+B_sourcemean;

% transform color space back to rgb
cformLAB2 = makecform('lab2srgb');
labSpace(:,:,1) = L_ItargetLAB;
labSpace(:,:,2) = A_ItargetLAB;
labSpace(:,:,3) = B_ItargetLAB;
rgbTarget = applycform(labSpace,cformLAB2);
%rgbTarget = lab2rgb(labSpace,'OutputType','uint8');

%%
figure()
subplot(1,2,2)
imshow(rgbTarget);
subplot(1,2,1)
imshow(Itarget);
