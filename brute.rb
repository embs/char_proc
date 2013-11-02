#!/usr/bin/ruby

module BruteForceMatching

  def self.match_string(t, p)
    t_i = 0 # index do texto
    p_i = 0 # index do padrao
    m = 0 # matches
    o = 0 # ocorrencias

    while t_i <= (t.size - p.size)
      while t[t_i] == p[p_i] && m < p.size
        m = m + 1
        t_i = t_i + 1
        p_i = p_i + 1
      end
      if m == p.size
        o = o + 1
      else
        t_i = t_i - m + 1
      end
      m = 0
      p_i = 0
    end

    o
  end

  def self.match_files(t, p)
    o = 0 # ocorrencias
    pattern = IO.readlines(p)[0]
    IO.readlines(t).each do |line|
      o = o + match_string(line, pattern)
    end

    o
  end
end

puts BruteForceMatching.match_files("shakespeare.txt", "pattern.txt")
