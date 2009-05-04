function plot_graphs(n)
  pop = loadfile(1); pop = length(pop(1,:));
  meanplot = figure('Name', 'Mean Cost','NumberTitle', 'off');title(['Mean Cost (Population ',num2str(pop),', ',num2str(n),' runs)']); xlabel('Generation'); ylabel('Cost'); hold on;
  medianplot = figure('Name', 'Median Cost','NumberTitle', 'off');title(['Median Cost (Population ',num2str(pop),', ',num2str(n),' runs)']); xlabel('Generation'); ylabel('Cost'); hold on;
  modalplot = figure('Name', 'Modal Cost','NumberTitle', 'off');title(['Modal Cost (Population ',num2str(pop),', ',num2str(n),' runs)']); xlabel('Generation'); ylabel('Cost'); hold on;
  minplot = figure('Name', 'Minimum Cost','NumberTitle', 'off');title(['Minimum Cost (Population ',num2str(pop),', ',num2str(n),' runs)']); xlabel('Generation'); ylabel('Cost'); hold on;
  for n = 1:n
    x = loadfile(n);
    figure(meanplot); plot(mean(x'));
    figure(modalplot); plot(mode(x'));
    figure(medianplot); plot(median(x'));
    figure(minplot); plot(min(x'));
    clear x;
  end
end

function data = loadfile(n)
  prefix = 'run_popsummary_';
  data = load([prefix,num2str(n)]);  
end