#!/usr/bin/env ruby -wKU

raise ArgumentError unless dataname = ARGV[0]  
raise ArgumentError unless runs = ARGV[1]

puts "Making Graphs with MATLAB"
cmd =
"plot_graphs(#{runs}, '#{dataname}')
exit"
puts cmd
puts `echo "#{cmd}" | matlab`

puts "Moving the files into a sorted location"
dir = "#{dataname}"
raise Exception unless `mkdir #{dir}` 
raise Exception unless `mv #{dataname}*.eps #{dir}`
#Convert the graph eps to pdf for pdftex compatibility
`cd #{dir} && ../epsconvert.rb`