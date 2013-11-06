#!/usr/bin/ruby

module BoyerMoore
	def self.bad_char(pattern)
		c = Array.new(256) { |n| n = -1 }
		pattern.length.times do |n|
			c[pattern[n].ord] = n
		end

		c
	end

	def self.board(pattern) #BUGADO
		m = pattern.length
		b = Array.new(m){ |n| n = -1 }
		i = 1
		j = 0
		while i < m
			while (i + j < m) && (pattern[i+j] == pattern[j])
				j = j + 1
				if b[i+j] == -1
					b[i+j] = j-1
				end
			end
			i = i + j - b[j]
			if b[j] > 0
				j = b[j]
			else
				j = 0
			end
		end

		b
	end

	def self.good_suffix(pattern)
		m = pattern.length
		pi = board(pattern) # pi[j] = |board(pattern[:j])|, 0 <= j <= m-1
		pr = board(pattern.reverse)
		d = []
		m.times do |j|
			d << (m - pi[m-1])
		end
		(m-1).times do |s|
			if j == m-1-pr[s-1] && d[j] > (s-pr[s-1])
				d[j] = s - pr[s-1]
			end
		end

		d
	end

	def self.match_string(string, pattern)
		c = bad_char(pattern)
		s = good_suffix(pattern) # s[j] |salto qnd diff na pos pat[j]
		occ = []

		n = string.length
		m = pattern.length

		i = 0
		while i < n
			j = m - 1
			while j >= 0 && string[i+j] = pattern[j]
				j = j - 1
			end
			if j == -1
				occ < i
				i = i + board(pattern)
			else
				i = [j - c[string[i+j]], s[]].max
			end
		end
	end
end
