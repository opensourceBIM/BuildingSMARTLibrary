package nl.tue.buildingsmart.express.population.test;

/******************************************************************************
 * Copyright (C) 2009-2016  BIMserver.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see {@literal<http://www.gnu.org/licenses/>}.
 *****************************************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.TreeSet;

import nl.tue.buildingsmart.express.dictionary.Namespaces;
import nl.tue.buildingsmart.express.population.EntityInstance;
import nl.tue.buildingsmart.express.population.ModelPopulation;

@SuppressWarnings("all")
public class PopulationMetrics {
	ModelPopulation pop;
	Namespaces nsConf;
	HashMap<String, Integer> namespaceMembers = new HashMap<String, Integer>();

	public PopulationMetrics(ModelPopulation pop, Namespaces nsConf) {

		this.pop = pop;
		this.nsConf = nsConf;
	}

	public void countEntitiesPerNamespace() {
		for (String ns : nsConf.getNamespaces()) {
			namespaceMembers.put(ns, new Integer(0));
		}
		for (EntityInstance entInst : pop.getInstances().values()) {
			String ns = nsConf.getNS(entInst.getEntityDefinition().getName());
			if(ns==null){
				System.out.println("Namespace for " + entInst.getEntityDefinition().getName() + " not found!");
			} else {
				namespaceMembers.put(ns, namespaceMembers.get(ns) + 1);
			}
		}
		TreeSet<String> ts = new TreeSet(namespaceMembers.keySet());
		for (String ns : ts) {

			System.out.println(ns + ";" + namespaceMembers.get(ns));
		}
	}

	public static void main(String[] args) throws IOException {
		if(args.length > 1 ){
			try(FileInputStream spf =  new FileInputStream(args[0])){
				Namespaces namespaces = new Namespaces(args[1]);
				if(namespaces.readNSConfig()){
					System.out.println("read ns config");
				} else {
					System.out.println("failed to read ns config");
				}
				for (String namespace: namespaces.getNamespaces()) {
					System.out.println(namespace);
					// System.out.println(String.join(", ", namespaces.getNamespaceMembers(namespace)));
				}
				ModelPopulation model = new ModelPopulation(spf);
				model.setSchemaFile(Paths.get("src", "schema", "IFC2X3_TC1.exp"));
				model.load();
				new PopulationMetrics(model, namespaces).countEntitiesPerNamespace();
			}
		}
	}
}
