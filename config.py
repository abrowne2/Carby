from algorithm import algo

#simple configuration
env = {}
env['host'] = '0.0.0.0'
env['port'] = 3000

def runMe(orig, dest):
    return algo.shotCaller(orig, dest)
