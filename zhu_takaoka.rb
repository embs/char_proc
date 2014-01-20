#!/usr/bin/ruby
require_relative 'boyer_moore.rb'
require 'benchmark'

class ZhuTakaoka < BoyerMoore
  def make_bad_char_table(pattern, alphabet)
    bad_char_table = Hash.new
    m = pattern.length
    alphabet.each do |ch1|
      alphabet.each do |ch2|
        if ch2 == pattern[1]
          bad_char_table[[ch1, ch2]] = m - 1
        else
          bad_char_table[[ch1, ch2]] = m
        end
      end
      2.upto(m) do |j|
        bad_char_table[[pattern[j-2], pattern[j-1]]] = m - j
      end
    end

    bad_char_table
  end

  def match(string, pattern, alphabet)
    occ = []
    char_inspects = 0

    # Pré-processamento
    bad_char_table = make_bad_char_table pattern, alphabet
    good_sufix_table = maked_good_sufix_table pattern

    i = pattern.length-1
    while i < string.length
      char_inspects = char_inspects+1
      j = pattern.length-1
      while (j >= 0) && (string[i] == pattern[j])
        i = i - 1
        j = j - 1
      end
      if j < 0 # match!
        occ << i+1
        i = i + pattern.length+1
      else
        i = i + [bad_char_table[[string[i], string[i+1]]], good_sufix_table[j]].max
      end
    end
    # puts "#{char_inspects}, #{string.length}"

    occ
  end
end

alphabet_10 = ['a','b','c','d','e','f','g','h','i','j']
alphabet_15 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o']
alphabet_20 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t']
alphabet_30 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3']
alphabet_40 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','-','/','*']
alphabet_50 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','-','/','*','!','@','#','$','%','&','(',')','_','-']
zt = ZhuTakaoka.new
# text_file = File.open "text_500k_10.txt"
# text = text_file.readline
# text_file.close
# pattern_file = File.open "pattern_8_10.txt"
# pattern = pattern_file.readline
# pattern_file.close
# puts "Pattern length: 8"
# puts (zt.match text, pattern, alphabet_10).inspect
# len = 10
# while len <= 100
#   pattern_file = File.open "pattern_#{len}_10.txt"
#   pattern = pattern_file.readline
#   pattern_file.close
#   puts "Pattern length: #{len}"
#   puts (zt.match text, pattern, alphabet_10).inspect
#   len = len + 5
# end

text_file = File.open "text_1kk_50.txt"
text = text_file.readline
text_file.close
pattern_file = File.open "pattern_400_50.txt"
pattern = pattern_file.readline
pattern_file.close
time = Benchmark.measure do
  zt.match text, pattern, alphabet_50
end
puts time
