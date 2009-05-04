function plot_graphs(reps, dataname)
  tmp = loadfile(1); pop = length(tmp(1,:));  gens = length(tmp(:,1)); clear tmp;
  meanplot = figure('Name', 'Mean Cost','NumberTitle', 'off');title(['Mean Cost (Population ',num2str(pop),', ',num2str(reps),' runs)']); xlabel('Generation'); ylabel('Cost'); hold on;
  medianplot = figure('Name', 'Median Cost','NumberTitle', 'off');title(['Median Cost (Population ',num2str(pop),', ',num2str(reps),' runs)']); xlabel('Generation'); ylabel('Cost'); hold on;
  modalplot = figure('Name', 'Modal Cost','NumberTitle', 'off');title(['Modal Cost (Population ',num2str(pop),', ',num2str(reps),' runs)']); xlabel('Generation'); ylabel('Cost'); hold on;
  minplot = figure('Name', 'Minimum Cost','NumberTitle', 'off');title(['Minimum Cost (Population ',num2str(pop),', ',num2str(reps),' runs)']); xlabel('Generation'); ylabel('Cost'); hold on;
  for n = 1:reps
    x = loadfile(n);
    figure(meanplot); plot(mean(x'));
    figure(modalplot); plot(mode(x'));
    figure(medianplot); plot(median(x'));
    figure(minplot); plot(min(x'));
    clear x;
  end
  
  saveas(meanplot, [dataname,'_p',num2str(pop),'_g',gens,'_r',num2str(reps),'_mean'] ,'eps')
  saveas(medianplot, [dataname,'_p',num2str(pop),'_g',gens,'_r',num2str(reps),'_median'] ,'eps')
  saveas(modalplot, [dataname,'_p',num2str(pop),'_g',gens,'_r',num2str(reps),'_modal'] ,'eps')
  saveas(minplot, [dataname,'_p',num2str(pop),'_g',gens,'_r',num2str(reps),'_min'] ,'eps')
  
end

function data = loadfile(n)
  prefix = 'run_popsummary_';
  data = load([prefix,num2str(n)]);  
end