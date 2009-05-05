#!/usr/bin/env ruby -wKU

raise ArgumentError unless sourcefilename = ARGV[0]
raise ArgumentError unless runs = ARGV[1]
raise ArgumentError unless dataname = ARGV[2]
raise ArgumentError unless dir = ARGV[3]  

puts "Making Graphs with MATLAB"
cmd =
"plot_graphs('#{dir}', #{runs}, '#{dataname}', '#{sourcefilename}')
exit"
puts cmd
puts `echo "#{cmd}" | matlab`

#the files are already there now so no need for this.
# puts "Moving the files into a sorted location"
# dir = "#{dataname}"
# raise Exception unless `mkdir #{dir}` 
# raise Exception unless `mv #{dataname}*.eps #{dir}`

#Convert the graph eps to pdf for pdftex compatibility
puts `cd #{dir} && ../epsconvert.rb`