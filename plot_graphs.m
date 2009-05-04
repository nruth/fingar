function plot_graphs(n)
  meanplot = figure('Name', 'Mean Cost','NumberTitle', 'off');hold on
  medianplot = figure('Name', 'Median Cost','NumberTitle', 'off');hold on
  modalplot = figure('Name', 'Modal Cost','NumberTitle', 'off');hold on
  minplot = figure('Name', 'Minimum Cost','NumberTitle', 'off');hold on
  for n = 1:n
    prefix = 'run_popsummary_';
    x = load(strcat(prefix,int2str(n)));
    figure(meanplot); plot(mean(x'));
    figure(modalplot); plot(mode(x'));
    figure(medianplot); plot(median(x'));
    figure(minplot); plot(min(x'));
    clear x;
  end
end