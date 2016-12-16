package blue

import groovy.json.*

class SliceController {

    def random = {
	def env = System.getenv()
	def vcs = env.grep{it.key=='VCAP_SERVICES'} 
	//VCAP_SERVICES looks like this:
	//[{"user-provided":[{ "credentials": { "password": "judy", "username": "craig" }, "syslog_drain_url": "", "volume_mounts": [ ], "label": "user-provided", "name": "myups", "tags": [ ] }]}]
	//This is a string:
	//{"user-provided":[{ "credentials": { "password": "judy", "username": "craig" }, "syslog_drain_url": "", "volume_mounts": [ ], "label": "user-provided", "name": "myups", "tags": [ ] }]}
	def jsonSlurper = new JsonSlurper()
	def object = jsonSlurper.parseText(vcs.value[0])
	def credentials = object."user-provided"[0].credentials 
	//credentials looks like this:
	//{password=judy, username=craig}
	[credjson: credentials, 
         credslashstring: credentials.username + '/' + credentials.password ]
    }

    def servers = {

	def untaggedInstances = [:]



	//def regions = ['us-west-2'] 
	//def regions = ['us-east-1']
	def regions = ['us-west-2','us-east-1']
	regions.each { region ->

	    def allProjectValuesAsNameCRLFValue = []
	    //The following returns a literal 'Project' on one line, then a value like 'beachfront-int' on the next line.
            def getAllProjectValuesAsNameCRLFValue = "aws ec2 describe-instances --filter Name=tag-key,Values=Project --query Reservations[].Instances[].[Tags] --region $region --output text".execute()
    	    allProjectValuesAsNameCRLFValue = getAllProjectValuesAsNameCRLFValue.text.split()
    	    int allProjectValuesAsNameCRLFValueSize = allProjectValuesAsNameCRLFValue.size()
    
    	    def allProjectValues = []
    	    for (int i=0; i<allProjectValuesAsNameCRLFValueSize; i += 2) {
    	        if (allProjectValuesAsNameCRLFValue[i] == 'Project') {
    		    allProjectValues.add(allProjectValuesAsNameCRLFValue[i+1])
    	        }
    	    }
    
    	    //Use a list of allInstances to determine which instancs are untagged,
    	    // because the CLI doesn't seem to allow the same wildcards that the EC2 Dashboard GUI filter window allows
    	    // e.g. "Not tagged" in "tag:Project : Not tagged"
    	    def allInstances = []
    	    def allTagged = [:]
    	    //The following returns one InstanceId value per line.
    	    def all = "aws ec2 describe-instances --query Reservations[].Instances[].[InstanceId] --region $region --output text".execute()
    	    allInstances = all.text.split()
    
    	    //Sort the Project values (e.g. beachfront-dev, piazza-int) just for println readability.
    	    def projects = allProjectValues.unique().sort()
    	    projects.each { p ->
    	        //The following returns one InstanceID value per line.
    	        def tagged = "aws ec2 describe-instances --filter Name=tag:Project,Values=$p --query Reservations[].Instances[].[InstanceId] --region $region --output text".execute()
    	        allTagged[p] = tagged.text.split()
    	    }
    
    	    untaggedInstances[region] = allInstances
    	    projects.each { p ->
    	        allTagged[p].each { i ->
		    def ilong = (i.size() == 10)? i+'         ' : i
    		    println "$region|$ilong|$p"
    	        }
    	        untaggedInstances[region] -= allTagged[p]
    	    }
    	    untaggedInstances[region].each { i ->
		def ilong = (i.size() == 10)? i+'         ' : i
    	        println "$region|$ilong|untagged"
    	    }
	}
	println "untaggedInstances:\n$untaggedInstances"
/*


	//A hardcoded def like this is a java.util.ArrayList:
	untaggedInstances['us-west-2'] = //I got this list by running the above code...
	['i-0f59fea1', 'i-507ba50f', 'i-d527807b', 'i-7a9e3ed4', 'i-788d75ec', 'i-e4aece39', 'i-d127807f', 'i-f64dfe62', 'i-f14dfe65', 'i-eb4efd7f', 'i-2b01db33', 'i-e1aece3c', 'i-1468b2ba', 'i-7a8d75ee', 'i-11c84fcc', 'i-424efdd6', 'i-148e7680', 'i-ff5736e7', 'i-5bd42ccf', 'i-568e76c2', 'i-988f770c', 'i-94078101', 'i-9e4f6486', 'i-558e76c1', 'i-7b8d75ef', 'i-178e7683', 'i-798e76ed', 'i-9daf5709', 'i-f04dfe64', 'i-e2abcb3f', 'i-ea4efd7e', 'i-e3aece3e', 'i-d5afcf08', 'i-2d7a12f0', 'i-413083d5', 'i-e2d52d76', 'i-09d52d9d', 'i-50096148', 'i-9b8f770f', 'i-977b134a', 'i-58d42ccc']
	untaggedInstances['us-east-1'] = //...us-east-1 takes about 60 seconds to generate, so it's faster to run the above code, then copy and paste this list here.
	['i-030eb255a5506fdaf', 'i-0a4bcba9650f701dc', 'i-044523e25dd444e33', 'i-0cb16d359dd75cf79', 'i-0370bee0d9a0663ec', 'i-0a87ca753349e8bfa', 'i-27c051bb', 'i-04df03c9bf0b27eaf', 'i-0fbf19970baf933fa', 'i-0d1ec2d8dfeb66fcd', 'i-00ea3b91c7399cac4', 'i-8f361008', 'i-015904664d018ed40', 'i-0f9c3f404c651d187', 'i-0bdc098f', 'i-0dfe7a7ba3c641a7f', 'i-133c1a94', 'i-09304d5a777f22c3f', 'i-0801bfc40500f1037', 'i-08a59495', 'i-6f874e78', 'i-06dd7c49c24434df2', 'i-0585be3cb5e49469b', 'i-0aae9f97', 'i-0ea692c30870f5e92', 'i-f2860a61', 'i-0c0c832ccf08337c7', 'i-034120a02fc4e6ee6', 'i-060ee1c522487514c', 'i-0fc0cd4c66a6e2b81', 'i-0aebb54daf139870f', 'i-0660a6a6e53c1ba7a', 'i-00e0695cf0075e476', 'i-0a9c13d69d4b2680e', 'i-0ab1861ff76fd8930', 'i-05a9c3824b4c04e2b', 'i-bfe4012d', 'i-0ec51494', 'i-2e053ab0', 'i-652792fd', 'i-06e128c2a5bb7b0f9', 'i-03c77951fb21d07b7', 'i-0d219901a2aaa165c', 'i-0851482f802a70089', 'i-0e2eb0884ec055f4c', 'i-97a2930a', 'i-0df1f4da50387acee', 'i-0a1913183d0f33781', 'i-0cca43fcdf84672e1', 'i-d4b7624e', 'i-c4b8dcf5', 'i-ace6033e', 'i-06f594381e5c793a4', 'i-055360a909c96a68e', 'i-074c4016ee85fc454', 'i-cb77bd51', 'i-0a52dbf45713c2c22', 'i-0fc0eee9100e1f550', 'i-03ccf851e6f57460b', 'i-2c5889b6', 'i-0560eca50e09cb059', 'i-0d55332547b374244', 'i-010aace0af883c95e', 'i-07ba6dfbf1370f39a', 'i-aebee629', 'i-0fed2a4fda03b23fd', 'i-07837dd991a79f8e3', 'i-00eaf1f9dccdda897', 'i-75a186f2', 'i-dfdc0d45', 'i-02e13a0862b19c75a', 'i-e0ffd47d', 'i-017bc1572ffed721a', 'i-0b23db7ad9bc5948c', 'i-09df8af5da287060f', 'i-03879c90', 'i-04e46ccd2605ab7ce', 'i-0fc1a7072d2680d3b', 'i-03d8284fb8a6e7166', 'i-0fea4726291d0942e', 'i-c82b0d4f', 'i-0ca028617407a1702', 'i-dd219445', 'i-003c3948d915bf797', 'i-04f2a0b36672f2253', 'i-022c272fe9e539960', 'i-0cd0daba42d2d2caf', 'i-04a07fbbc084a8918', 'i-09cdb64e2a89ce114', 'i-0b5a716beccb22483', 'i-014533c19e6fda1d1', 'i-fa824bed', 'i-07dbe3ff2d2dadc08', 'i-bea09123', 'i-08605d286e8f9e737', 'i-bfc613fa', 'i-0a004f27658cb368f', 'i-0b94fc47e1f61310e', 'i-c35133f2', 'i-09c6d3215910136df', 'i-0bcfeb8e5fe6dffb3', 'i-09e165ea580486d3e', 'i-022fd4846ca3034b9', 'i-0cc1e5b3710dbe4cd', 'i-01806093dbb0790de', 'i-0a72ce45a24a0e63d', 'i-0b7f6f2ecde4820bb', 'i-fca1867b', 'i-0c947c8662cbe4f0c', 'i-0cb50e1db86ee1865']
*/

        def tagme = [:]
	tagme['us-west-2'] = [
	    //These rules use the key-pair to infer the project:
	    'Name=key-name,Values=adam-chou-key' :'piazza-dev',
	    'Name=key-name,Values=afroje-initial':'piazza-dev',
	    'Name=key-name,Values=akey-initial'  :'piazza-dev',
	    'Name=key-name,Values=ccri-gs'       :'xterrain-dev',
	    'Name=key-name,Values=craigradiantblueoregon':'piazza-dev',
	    'Name=key-name,Values=crunchy-admin' :'crunchy-dev',
	    'Name=key-name,Values=gs-salt*'      :'unknown-dev',
	    'Name=key-name,Values=peizer'        :'beachfront-dev',
	    'Name=key-name,Values=smithwinserve' :'piazza-dev',
	    'Name=key-name,Values=stresstest'    :'unknown-dev',
	]
	tagme['us-east-1'] = [
	    //These rules use the key-pair to infer the project:
	    'Name=key-name,Values=celery*'       :'eventkit-dev',
	    'Name=key-name,Values=geowave*'      :'geowave-dev',
	    'Name=key-name,Values=gsp-vpc'       :'piazza-dev',
	    'Name=key-name,Values=legion*'       :'legion-dev',
	    'Name=key-name,Values=mrgeo'         :'mrgeo-dev',
	    'Name=key-name,Values=packer*'       :'piazza-dev',
	    //These rules use the tag Name to infer the project:
	    'Name=tag:Name,Values=gsp-bastion'   :'piazza-dev',
	    'Name=tag:Name,Values=idaho*'        :'gbdx-dev',
	]

	untaggedInstances.each { uk, uv ->
	    def untaggedInstancesAsArrayList = uv.collect{it}

	    tagme[uk].each { rk, rv ->
	        //The following returns a string of whitespace-separated InstanceIds.
	        def peizerWhitespaceSeparatedInstanceIds = "aws ec2 describe-instances --filter $rk --query Reservations[].Instances[].InstanceId --region $uk --output text".execute()
		//println "#aws ec2 describe-instances --filter $rk --query Reservations[].Instances[].InstanceId --region $uk --output text"
	        def peizerInstanceIds = peizerWhitespaceSeparatedInstanceIds.text.split()
	        def peizerInstanceIdsAsArrayList = peizerInstanceIds.collect{it}
	        def intersekt = peizerInstanceIdsAsArrayList.intersect(untaggedInstancesAsArrayList)
	        def mycmd = "aws ec2 create-tags --dry-run --resources ${intersekt.join(' ')} --tags Key=Project,Value=$rv --region $uk " 
	        println mycmd
	    }
	}

        render "hi3"
    }
}
