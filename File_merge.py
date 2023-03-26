f1 = open("2010-2014.txt")
f2 = open("2015-2017.txt")
f3 = open("2018-2020.txt")
finalF = open("allCoords.txt", "w+")

for i in range(2):
    f1.readline()
    f2.readline()
    f3.readline()

f1Str = f3.read().replace("	", " ").replace(",", ".")

finalF.writelines(f1)
finalF.writelines(f2)
finalF.write(f1Str)