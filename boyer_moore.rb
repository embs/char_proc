#!/usr/bin/ruby
require 'benchmark'

class BoyerMoore

	def make_bad_char_table(pattern, alphabet)
		bad_char_table = Hash.new
		alphabet.each do |char|
			pattern_index = pattern.length - 1
			pattern.length.times do
				bad_char_table[char] ||= pattern_index if pattern[pattern_index] == char
				pattern_index = pattern_index - 1
			end
			bad_char_table[char] ||= pattern.length
		end

		bad_char_table
	end

	def is_prefix?(string, index)
		suffix_length = string.length - index
		suffix_length.times do |i|
			return false if string[i] != string[index+i]
		end

		true
	end

	def suffix_length(string, index)
		len = 0
		while (string[index-len] == string[string.length-1-len]) && (len < index)
			len = len + 1
		end

		len
	end

	def maked_good_sufix_table(pattern)
		good_sufix_table = Hash.new
		last_prefix_index = pattern.length-1

		p = pattern.length - 1
		while p >= 0
			last_prefix_index = p + 1 if is_prefix? pattern, (p + 1)
			good_sufix_table[p] = last_prefix_index + (pattern.length-1-p)
			p = p - 1
		end

		(pattern.length-1).times do |p|
			slen = suffix_length pattern, p
			if pattern[p - slen] != pattern[pattern.length-1-slen]
				good_sufix_table[pattern.length-1-slen] = pattern.length-1-p+slen
			end
		end

		good_sufix_table
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
				i = i + [(bad_char_table[string[i]]), (good_sufix_table[j])].max
			end
		end
		# puts "#{char_inspects}, #{string.length}"

		occ
	end
end

# alphabet_10 = ['a','b','c','d','e','f','g','h','i','j']
# alphabet_15 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o']
# alphabet_20 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t']
# alphabet_30 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3']
# alphabet_40 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','-','/','*']
# alphabet_50 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','-','/','*','!','@','#','$','%','&','(',')','_','-']
# bm = BoyerMoore.new
# text_file = File.open "text_500k_10.txt"
# text = text_file.readline
# text_file.close
# pattern_file = File.open "pattern_8_10.txt"
# pattern = pattern_file.readline
# pattern_file.close
# puts "Pattern length: 8"
# puts (bm.match text, pattern, alphabet_10).inspect
# len = 10
# while len <= 100
# 	pattern_file = File.open "pattern_#{len}_10.txt"
# 	pattern = pattern_file.readline
# 	pattern_file.close
# 	puts "Pattern length: #{len}"
# 	puts (bm.match text, pattern, alphabet_10).inspect
# 	len = len + 5
# end

# text_file = File.open "text_1kk_50.txt"
# text = text_file.readline
# text_file.close
# pattern_file = File.open "pattern_400_50.txt"
# pattern = pattern_file.readline
# pattern_file.close
# time = Benchmark.measure do
# 	bm.match text, pattern, alphabet_50
# end
# puts time
