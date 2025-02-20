/*
 * LIMES Core Library - LIMES – Link Discovery Framework for Metric Spaces.
 * Copyright © 2011 Data Science Group (DICE) (ngonga@uni-paderborn.de)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.aksw.limes.core.measures.mapper.temporal.allenAlgebra;

import org.aksw.limes.core.execution.engine.ExecutionEngine;
import org.aksw.limes.core.execution.engine.SimpleExecutionEngine;
import org.aksw.limes.core.execution.planning.planner.DynamicPlanner;
import org.aksw.limes.core.io.cache.ACache;
import org.aksw.limes.core.io.cache.Instance;
import org.aksw.limes.core.io.cache.MemoryCache;
import org.aksw.limes.core.io.ls.LinkSpecification;
import org.aksw.limes.core.io.mapping.AMapping;
import org.aksw.limes.core.io.mapping.MappingFactory;
import org.aksw.limes.core.measures.measure.temporal.allenAlgebra.DuringReverseMeasure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class DuringReverseMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(DuringReverseMapperTest.class);
    public ACache source = new MemoryCache();
    public ACache target = new MemoryCache();

    @Before
    public void setUp() {
        source = new MemoryCache();
        target = new MemoryCache();
        // create source cache
        source.addTriple("S1", "beginsAtDateTime", "2015-05-20T08:21:04+02:00");
        source.addTriple("S1", "endsAtDateTime", "2015-05-20T08:22:04+02:00");

        source.addTriple("S2", "beginsAtDateTime", "2015-05-20T08:21:04+02:00");
        source.addTriple("S2", "endsAtDateTime", "2015-05-20T08:22:04+02:00");

        source.addTriple("S3", "beginsAtDateTime", "2015-05-20T08:24:04+02:00");
        source.addTriple("S3", "endsAtDateTime", "2015-05-20T08:25:04+02:00");

        source.addTriple("S4", "beginsAtDateTime", "2015-05-20T08:31:04+02:00");
        source.addTriple("S4", "endsAtDateTime", "2015-05-20T08:32:04+02:00");

        source.addTriple("S5", "beginsAtDateTime", "2015-05-20T09:21:04+02:00");
        source.addTriple("S5", "endsAtDateTime", "2015-05-20T09:24:04+02:00");

        source.addTriple("S6", "beginsAtDateTime", "2015-05-20T08:51:04+02:00");
        source.addTriple("S6", "endsAtDateTime", "2015-05-20T09:24:04+02:00");

        source.addTriple("S7", "beginsAtDateTime", "2015-05-20T08:41:04+02:00");
        source.addTriple("S7", "endsAtDateTime", "2015-05-20T08:51:04+02:00");

        source.addTriple("S8", "beginsAtDateTime", "2015-05-20T08:41:04+02:00");
        source.addTriple("S8", "endsAtDateTime", "2015-05-20T08:43:04+02:00");

        source.addTriple("S9", "beginsAtDateTime", "2015-05-20T08:21:04+02:00");
        source.addTriple("S9", "endsAtDateTime", "2015-05-20T08:34:04+02:00");

        source.addTriple("S10", "beginsAtDateTime", "2015-05-20T09:21:04+02:00");
        source.addTriple("S10", "endsAtDateTime", "2015-05-20T09:22:04+02:00");

        source.addTriple("S11", "beginsAtDateTime", "2015-05-20T09:21:04+02:00");
        source.addTriple("S11", "endsAtDateTime", "2015-05-20T09:22:04+02:00");

        source.addTriple("S12", "beginsAtDateTime", "2015-05-20T08:31:04+02:00");
        source.addTriple("S12", "endsAtDateTime", "2015-05-20T08:45:04+02:00");


        target.addTriple("S1", "b", "2015-05-20T08:21:04+02:00");
        target.addTriple("S1", "e", "2015-05-20T08:22:04+02:00");

        target.addTriple("S2", "b", "2015-05-20T08:21:04+02:00");
        target.addTriple("S2", "e", "2015-05-20T08:22:04+02:00");

        target.addTriple("S3", "b", "2015-05-20T08:24:04+02:00");
        target.addTriple("S3", "e", "2015-05-20T08:25:04+02:00");

        target.addTriple("S4", "b", "2015-05-20T08:31:04+02:00");
        target.addTriple("S4", "e", "2015-05-20T08:32:04+02:00");

        target.addTriple("S5", "b", "2015-05-20T09:21:04+02:00");
        target.addTriple("S5", "e", "2015-05-20T09:24:04+02:00");

        target.addTriple("S6", "b", "2015-05-20T08:51:04+02:00");
        target.addTriple("S6", "e", "2015-05-20T09:24:04+02:00");

        target.addTriple("S7", "b", "2015-05-20T08:41:04+02:00");
        target.addTriple("S7", "e", "2015-05-20T08:51:04+02:00");

        target.addTriple("S8", "b", "2015-05-20T08:41:04+02:00");
        target.addTriple("S8", "e", "2015-05-20T08:43:04+02:00");

        target.addTriple("S9", "b", "2015-05-20T08:21:04+02:00");
        target.addTriple("S9", "e", "2015-05-20T08:34:04+02:00");

        target.addTriple("S10", "b", "2015-05-20T09:21:04+02:00");
        target.addTriple("S10", "e", "2015-05-20T09:22:04+02:00");

        target.addTriple("S11", "b", "2015-05-20T09:21:04+02:00");
        target.addTriple("S11", "e", "2015-05-20T09:22:04+02:00");

        target.addTriple("S12", "b", "2015-05-20T08:31:04+02:00");
        target.addTriple("S12", "e", "2015-05-20T08:45:04+02:00");
    }

    @After
    public void tearDown() {
        source = null;
        target = null;
    }

    @Test
    public void simpleLS() {
        logger.info("{}","simpleLS");
        LinkSpecification ls = new LinkSpecification(
                "tmp_during_reverse(x.beginsAtDateTime|endsAtDateTime,y.b|e)",
                1.0);
        DynamicPlanner p = new DynamicPlanner(source, target);
        ExecutionEngine e = new SimpleExecutionEngine(source, target, "?x", "?y");
        AMapping m = e.execute(ls, p);
        logger.info("{}",m);

    }

    @Test
    public void similarity() {
        logger.info("{}","simpleLS");
        LinkSpecification ls = new LinkSpecification(
                "tmp_during_reverse(x.beginsAtDateTime|endsAtDateTime,y.b|e)",
                1.0);
        DynamicPlanner p = new DynamicPlanner(source, target);
        ExecutionEngine e = new SimpleExecutionEngine(source, target, "?x", "?y");
        AMapping m = e.execute(ls, p);
        logger.info("{}",m);

        AMapping m2 = MappingFactory.createDefaultMapping();
        for (Instance s : source.getAllInstances()) {
            for (Instance t : target.getAllInstances()) {
                DuringReverseMeasure measure = new DuringReverseMeasure();
                double sim = measure.getSimilarity(s, t, "beginsAtDateTime|endsAtDateTime", "b|e");
                if (sim != 0)
                    m2.add(s.getUri(), t.getUri(), sim);
            }
        }
        assertTrue(m.equals(m2));
    }

    @Test
    public void reverse() {
        logger.info("{}","reverse");
        LinkSpecification ls = new LinkSpecification(
                "tmp_during_reverse(x.beginsAtDateTime|endsAtDateTime,y.beginsAtDateTime|endsAtDateTime)", 1.0);
        DynamicPlanner p = new DynamicPlanner(source, target);
        ExecutionEngine e = new SimpleExecutionEngine(source, target, "?x", "?y");
        AMapping m = e.execute(ls, p);
        logger.info("{}",m);
        //////////////////////////////////////////////////////////////////////////////////////////////////
        LinkSpecification ls2 = new LinkSpecification(
                "tmp_during(x.beginsAtDateTime|endsAtDateTime,y.beginsAtDateTime|endsAtDateTime)", 1.0);
        AMapping m2 = e.execute(ls2, p);
        AMapping m3 = MappingFactory.createDefaultMapping();
        for (String s : m2.getMap().keySet()) {
            for (String t : m2.getMap().get(s).keySet()) {
                m3.add(t, s, 1);
            }
        }

        logger.info("{}",m3);
        assertTrue(m.equals(m3));
    }


}
