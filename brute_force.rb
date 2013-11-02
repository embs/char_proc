#!/usr/bin/ruby

text_file = File.open(ARGV[0], 'r')
pattern_file = File.open(ARGV[1], 'r')

debug = false
text_size = text_file.size
pattern_size = pattern_file.size
matches_counter = 0
t_index = 0
buffer = Array.new
occurencies = 0

while t_index <= (text_size - pattern_size) do
  t_char = text_file.getc
  p_char = pattern_file.getc
  puts t_index.to_s + ": comparando " + t_char + " e " + p_char if debug
  while t_char == p_char && matches_counter < pattern_size
    puts t_index.to_s+": matched! (" + t_char + " and " + p_char + ")" if debug
    matches_counter = matches_counter + 1
    t_index = t_index + 1
    buffer << t_char
    t_char = text_file.getc
    p_char = pattern_file.getc
  end
  if buffer.size == pattern_size
    text_file.ungetc(t_char)
    t_index = t_index - 1
    occurencies = occurencies + 1
    buffer = Array.new
  end
  if buffer.length > 0
    buffer = buffer.drop(1)
    buffer.reverse!
    puts buffer.inspect if debug
    text_file.ungetc(t_char)
    t_index = t_index - 1
    buffer.each do |c|
      text_file.ungetc(c)
      t_index = t_index - 1
    end
  end
  pattern_file.rewind
  buffer = Array.new
  matches_counter = 0
  t_index = t_index + 1
end

puts occurencies.to_s + " occurencie(s)."

pattern_file.close
text_file.close
