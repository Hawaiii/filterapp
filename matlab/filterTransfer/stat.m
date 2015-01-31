function output = stat(lap_target)
lmean = mean(lap_target(1,:));
lstd = std(lap_target(1,:));
alphamean = mean(lap_target(2,:));
alphastd = std(lap_target(2,:));
betamean = mean(lap_target(3,:));
betastd = std(lap_target(3,:));

output = [lmean,alphamean,betamean;lstd,alphastd,betastd];