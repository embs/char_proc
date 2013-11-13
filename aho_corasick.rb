#!/usr/bin/ruby

module AhoCorasick
	def self.transitions_table(pattern, alphabet)
		l = alphabet.length # tamanho do alfabeto
		n = pattern.length
		tT = Hash.new
		alphabet.each do |char|
			tT[[0, char]] = 0
		end
		n.times do |j|
			c = pattern[j]
			prev = tT[[j, c]]
			tT[[j,c]] = j + 1
			alphabet.each do |char|
				tT[[j+1, char]] = tT[[prev, char]]
			end
		end

		tT
	end

	def self.match_sring(string, pattern, alphabet)
		tT = transitions_table(pattern, alphabet)
		s = 0
		n = string.length
		m = pattern.length
		occ = []
		n.times do |i|
			s = tT[[s, string[i]]]
			occ << (i - m + 1) if s == m
		end

		occ
	end
end

a = AhoCorasick.transitions_table("aba", ['a', 'b'])
# a = AhoCorasick.match_sring("abracadabra", "abr", ['a','b','c','d','r'])
puts a.inspect
