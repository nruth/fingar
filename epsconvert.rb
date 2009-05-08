#!/usr/bin/env ruby -wKU
Dir.open('.') do |dir| 
  
  puts "Converting EPS files to PDF"
  
  dir.each do |file| 
    if file =~ /.eps$/
      pdf = "#{File.basename(file, ".eps")}.pdf"
      cmd = "epstopdf #{file} > #{pdf}"
      unless File.exists?(pdf)
        puts cmd
        puts `#{cmd}`
      end
    end
  end
  
  puts "EPS & PDF in directory:"
  puts `ls *.pdf *.eps`
end
