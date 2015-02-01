clear all
clc
close all
source = im2double(imread('source/rainbow.png'))*255;
target = im2double(imread('target/flower.jpg'))*255;
source = imresize(source,0.1);
% target = imresize(target,0.1);
[row,col,~] = size(target);

M = [0.5141,0.3239,0.1604;0.2651,0.6702,0.0641;0.0241,0.1228,0.8444];
L = [0.3897,0.6890,-0.0787;-0.2298,1.1834,0.0464;0.0000,0.0000,1.0000];
A = [1/sqrt(3),0,0;0,1/sqrt(6),0;0,0,1/sqrt(2)];
B = [1,1,1;1,1,-2;1,-1,0];

lap_target = transfer(M,L,A,B,target);
lap_source = transfer(M,L,A,B,source);

%% mean calculation for each channel

output_target = stat(lap_target);
output_source = stat(lap_source);

l_no_mean = lap_target(1,:)-output_target(1,1);
a_no_mean = lap_target(2,:)-output_target(1,2);
b_no_mean = lap_target(3,:)-output_target(1,3);

l_scale = output_source(2,1)/output_target(2,1)*l_no_mean;
a_scale = output_source(2,2)/output_target(2,2)*a_no_mean;
b_scale = output_source(2,3)/output_target(2,3)*b_no_mean;

l_scale = l_scale + output_source(1,1);
a_scale = a_scale + output_source(1,2);
b_scale = b_scale + output_source(1,3);

lap_out = [l_scale;a_scale;b_scale];

output_lapout = stat(lap_out)

% debug = merge(10.^lap_out,row,col);
% figure()
% imshow(debug)
%% convert back
Ainv = [sqrt(3)/3,0,0;0,sqrt(6)/6,0;0,0,sqrt(2)/2];
Binv = [1,1,1;1,1,-1;1,-2,0];
LMS_target = Binv*Ainv*lap_out;
LMS_target = 10.^LMS_target;
RGB_target = inv(L*M)*LMS_target;

RGB_result = merge(RGB_target,row,col);

%%
figure()
imshow(RGB_result/255);
figure()
imshow(source/255);
figure()
imshow(target/255)