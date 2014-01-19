#!/usr/bin/ruby
require_relative 'boyer_moore.rb'

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

    # Pré-processamento
    bad_char_table = make_bad_char_table pattern, alphabet
    good_sufix_table = maked_good_sufix_table pattern

    i = pattern.length-1
    while i < string.length
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

    occ
  end
end
