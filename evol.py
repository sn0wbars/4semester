import string
import random
import time

target = "randomString"
maxEpoch = 4000
strLen = len(target)

def randL():
	return random.choice(string.printable + ' ')

def randStr(len):
	return ''.join([randL() for _ in range(len)])

def crtPop(size, len = strLen):
	return [{'dna': x, 'fit': cost(x, target)} for x in [randStr(len) for _ in range(size)] ] # list_comprehension

def cost(source, targ = target):
	return reduce(lambda x,y: x+y, map(lambda x,y: (ord(x)- ord(y))**2, source, targ)) #functional programming

def mutate(source):
	i = random.randrange(len(source))
	return source[:i] + chr(ord(source[i]) + random.randrange(-1,2)) + source[i+1:]#possible null-mutation

def cross(par1, par2, k = 2): #KPointCrossover
	if(len(par1) != len(par2)):
		return none
	child_dna = par1[:]
	points = sorted(random.sample(range(len(par1)), k))
	for point1, point2 in zip(points[::2], points[1::2]):
		child_dna = child_dna[:point1] + par2[point1:point2] + child_dna[point2:]
	child_dna = mutate(child_dna)
	return child_dna

def evol(source = randStr(len(target))):
	print (source)
	source = source[:len(target)]
	fitVal = cost(source, target)

	for i in range(maxEpoch):
		time.sleep(0.001)
		current = mutate(source)
		curFit = cost(current, target)
		if (curFit < fitVal):
			fitVal = curFit
			source = current
			print("{}: {}".format(i, source))
		if (curFit == 0):
			return 0

def elitEvol(k = 100):
	pop = crtPop(k)

	for i in range(maxEpoch):
		pop.sort(key = lambda x: x['fit'])
		if pop[0]['fit'] == 0:
			return 0;
		par1 = pop[int(random.random()*random.random()*(len(pop))-1)]['dna']
		par2 = pop[int(random.random()*random.random()*(len(pop))-1)]['dna']
		child_dna = cross(par1, par2, 4)
		print(child_dna)
		child = {'dna': child_dna, 'fit' : cost(child_dna)}
		if (child['fit'] < pop[-1]['fit']):
			pop[-1] = child
			print ('{}: {}'.format(i,pop))

	# pop.s

#target = "Hello world";

