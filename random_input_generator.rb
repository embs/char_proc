#!/usr/bin/ruby

class RandomInputGenerator
  def write_file(length, alphabet, output_filename)
    file = File.new(output_filename, "w")
    out = ""
    length.times do
      out = out + alphabet[rand(alphabet.length)]
    end
    file.puts out
    file.close
  end
end

alphabet_10 = ['a','b','c','d','e','f','g','h','i','j']
alphabet_15 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o']
alphabet_20 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t']
alphabet_30 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3']
alphabet_40 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','-','/','*']
alphabet_50 = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','-','/','*','!','@','#','$','%','&','(',')','_','-']

rig = RandomInputGenerator.new
# rig.write_file(10, ['a', 'b', 'c', 'd', 'e'], "pattern_10_5.txt")
rig.write_file(1000000, alphabet_50, "text_1kk_50.txt")
# len = 10
# while len <= 100
#   rig.write_file(len, alphabet_10, "pattern_#{len}_10.txt")
#   len = len + 5
# end
