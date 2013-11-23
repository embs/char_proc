#!/usr/bin/ruby

module EditDistance
  def self.calculate(x, y)
    matrix = Hash.new
    (y.length + 1).times do |n| # incializa primeira linha
      matrix[[0,n]] = n
    end
    (x.length + 1).times do |n| # inicializa primeira coluna
      matrix[[n,0]] = n
    end
    1.upto(x.length) do |m|
      1.upto(y.length) do |n|
        phi = x[m-1] == y[n-1] ? 0 : 1
        val = [(matrix[[m-1,n-1]] + phi), (matrix[[m-1, n]] + 1),
          (matrix[[m, n-1]] + 1)].min
        matrix[[m,n]] = val
      end
    end

    matrix
  end

  def self.tryHarder(x, y)
    matrix = Hash.new
    (x.length + 1).times do |n| # inicializa primeira coluna
      matrix[[n,0]] = n
    end
    1.upto(y.length) do |n|
      matrix[[0,1]] = n # ajusta elementos da primeira linha
      1.upto(x.length) do |m|
        phi = x[m-1] == y[n-1] ? 0 : 1
        # puts x[m-1].to_s + " " + y[n-1].to_s
        val = [(matrix[[m-1,0]] + phi), (matrix[[m, 0]] + 1),
          (matrix[[m-1, 1]] + 1)].min
        matrix[[m,1]] = val
        matrix[[m-1,0]] = matrix[[m-1,1]] # atualiza matriz
      end
      matrix[[x.length,0]] = matrix[[x.length,1]]
    end

    matrix
  end
end

puts EditDistance.tryHarder("CADA", "ABADAC").inspect
